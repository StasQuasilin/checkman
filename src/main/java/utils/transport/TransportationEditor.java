package utils.transport;

import constants.Constants;
import entity.Worker;
import entity.documents.DealProduct;
import entity.log.comparators.TransportComparator;
import entity.organisations.Address;
import entity.organisations.Organisation;
import entity.transport.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.DocumentUIDGenerator;
import utils.NoteUtil;
import utils.UpdateUtil;
import utils.hibernate.dao.DealDAO;
import utils.hibernate.dao.TransportationDAO;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;
import utils.json.JsonObject;

import java.sql.Date;
import java.util.*;

import static constants.Constants.*;

public class TransportationEditor {
    private final Logger log = Logger.getLogger(TransportationEditor.class);
    private final dbDAO dao = dbDAOService.getDAO();
    private final TransportComparator transportComparator = new TransportComparator();
    private final NoteUtil noteUtil = new NoteUtil();
    private final UpdateUtil updateUtil = new UpdateUtil();
    private final TransportationDAO transportationDAO = new TransportationDAO();
    private final DealDAO dealDAO = new DealDAO();

    public Transportation saveTransportation(JSONObject json, Worker creator) {

        boolean save = false;
        boolean isNew = false;

        Transportation transportation = dao.getObjectById(Transportation.class, json.get(ID));
        if (transportation == null){
            isNew = true;

            transportation = TransportUtil.createTransportation(creator);
            transportComparator.fix(null);
        } else {
            transportComparator.fix(transportation);
        }

        if (json.containsKey(ADDRESS)){
            Address address = dao.getObjectById(Address.class, json.get(ADDRESS));
            if (transportation.getAddress() == null || transportation.getAddress().getId() != address.getId()){
                transportation.setAddress(address);
                save = true;
            }
        } else if (transportation.getAddress() != null){
            transportation.setAddress(null);
            save = true;
        }

        Date date = Date.valueOf(String.valueOf(json.get(Constants.DATE)));
        if (transportation.getDate() == null || !transportation.getDate().equals(date)) {
            transportation.setDate(date);
            save = true;
        }

        TransportCustomer customer;
        if (json.containsKey(CUSTOMER)){
            customer = TransportCustomer.valueOf(String.valueOf(json.get(CUSTOMER)));
        } else {
            customer = TransportCustomer.szpt;
        }

        if (customer == TransportCustomer.contragent){
            customer = TransportCustomer.cont;
        }

        if (transportation.getCustomer() != customer) {
            transportation.setCustomer(customer);
            save = true;
        }

        Trailer trailer = dao.getObjectById(Trailer.class, json.get(TRAILER));
        final Trailer t = transportation.getTrailer();

        if (changeIt(trailer, t)){
            TransportUtil.setTrailer(transportation, trailer);
            save = true;
        }

        Vehicle vehicle = dao.getObjectById(Vehicle.class, json.get(VEHICLE));
        final Vehicle v = transportation.getVehicle();

        if (changeIt(vehicle, v)){
            TransportUtil.setVehicle(transportation, vehicle);
            save = true;
        }

        Organisation transporter = dao.getObjectById(Organisation.class, json.get(TRANSPORTER));
        if (transporter != null){
            TransportUtil.setTransporter(transportation, transporter);
            save = true;
        }

        Driver driver = dao.getObjectById(Driver.class, json.get(DRIVER));
        final Driver d = transportation.getDriver();
        if (changeIt(driver, d)) {
            TransportUtil.setDriver(transportation, driver, transporter == null);
            save = true;
        }

        final NoteEditor noteEditor = new NoteEditor(transportation);

        if (json.containsKey(NOTES)){
            for (Object o :(JSONArray) json.get(NOTES)){
                if (noteEditor.saveNote(new JsonObject(o), creator)){
                    save = true;
                }
            }
            noteEditor.clear();
        }
        Worker manager = null;
        if (json.containsKey(MANAGER)){
            manager = dao.getObjectById(Worker.class, json.get(MANAGER));
        }
        if (transportation.getManager() != manager){
            transportation.setManager(manager);
            save = true;
        }

        if (save) {
            transportationDAO.saveTransportation(transportation, isNew);
        }
        final boolean update = saveTransportationProducts((JSONArray) json.get(PRODUCTS), transportation);
        if (save || update){
            updateUtil.onSave(transportation);
        }

        transportComparator.compare(transportation, creator);

        return transportation;
    }

    private boolean changeIt(Object o1, Object o2) {
        if (o1 == null){
            return o2 != null;
        } else {
            return !o1.equals(o2);
        }
    }

    private HashMap<Integer, TransportationProduct> buildProductMap(Transportation transportation) {
        final HashMap<Integer, TransportationProduct> hashMap = new HashMap<>();
        final Set<TransportationProduct> products = transportation.getProducts();
        if(products != null) {
            for (TransportationProduct product : products) {
                hashMap.put(product.getId(), product);
            }
        }
        return hashMap;
    }

    public boolean saveTransportationProducts(JSONArray array, Transportation transportation) {

        final HashMap<Integer, TransportationProduct> hashMap = buildProductMap(transportation);
        final Set<TransportationProduct> products = transportation.getProducts();
        final int productsCount = products.size();
        products.clear();

        int index = 0;
        boolean update = false;
        for (Object o : array){
            JsonObject object = new JsonObject((JSONObject) o);
            final int id = object.getInt(ID);
            TransportationProduct product = hashMap.remove(id);
            boolean saveIt = false;

            final DealProduct dealProduct = dealDAO.getDealProduct(object.getInt(DEAL_PRODUCT));
            if(dealProduct == null){
                log.warn("--- No deal product for " + index + " product ");
                continue;
            }

            if(product == null){
                product = new TransportationProduct();
                product.setTransportation(transportation);
                product.setUid(DocumentUIDGenerator.generateUID());
                product.setAddress(dealProduct.getAddress());
            }

            final DealProduct dp = product.getDealProduct();
            if (changeIt(dp, dealProduct)){
                product.setDealProduct(dealProduct);
                saveIt = true;
            }
            final float amount = object.getFloat(AMOUNT);
            if (product.getAmount() != amount){
                product.setAmount(amount);
                saveIt = true;
            }
            if(object.contain(ADDRESS_ID)){
                final int addressId = object.getInt(ADDRESS_ID);
                final Address address = dao.getObjectById(Address.class, addressId);
                product.setAddress(address);
                saveIt = true;
            } else {
                final Address loadAddress = product.getAddress();
                if(loadAddress != null){
                    product.setAddress(null);
                    saveIt = true;
                }
            }


            if (product.getIndex() != index){
                product.setIndex(index);
                saveIt = true;
            }

            if(saveIt){
                update = true;
                transportationDAO.saveProduct(product);
            }
            index++;
            products.add(product);
        }
        for (TransportationProduct product : hashMap.values()){
            transportationDAO.removeProduct(product);
        }

        if (!update){
            update = productsCount != products.size();
        }

        return update;
    }
}
