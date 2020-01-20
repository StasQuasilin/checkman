package utils.transport;

import constants.Constants;
import entity.Worker;
import entity.deal.Contract;
import entity.deal.ContractProduct;
import entity.organisations.Organisation;
import entity.transport.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.DateUtil;
import utils.DocumentUIDGenerator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 04.12.2019.
 */
public class TransportationSaver implements Constants{

    private final dbDAO dao = dbDAOService.getDAO();
    private final Logger log = Logger.getLogger(TransportationSaver.class);

    public synchronized Transportation2 saveTransportation(JSONObject body, Worker worker) {
        Transportation2 transportation = dao.getObjectById(Transportation2.class, body.get(ID));
        if (transportation == null){
            log.info("New transportation");
            transportation = new Transportation2();
            transportation.setUid(DocumentUIDGenerator.generateUID());
            transportation.setCreateTime(new ActionTime(worker));
            transportation.setManager(worker);
        } else {
            log.info("Edit transportation: " + transportation.getId());
        }
        boolean save = false;
        Date date = Date.valueOf(String.valueOf(body.get(DATE)));
        if (TransportUtil.setDate(transportation, date)){
            save = true;
        }

        Trailer trailer = dao.getObjectById(Trailer.class, body.get(TRAILER));
        if (TransportUtil.setTrailer(transportation, trailer)){
            save = true;
        }

        Vehicle vehicle = dao.getObjectById(Vehicle.class, body.get(VEHICLE));
        if (TransportUtil.setVehicle(transportation, vehicle)){
            save = true;
        }

        Driver driver = dao.getObjectById(Driver.class, body.get(DRIVER));
        if (TransportUtil.setDriver(transportation, driver)){
            save = true;
        }

        if (driver != null) {
            String license = String.valueOf(body.get(LICENSE));
            if (TransportUtil.setLicense(driver, license)) {
                save = true;
            }
        }

        if (body.containsKey(TRANSPORTER)) {
            Organisation transporter = dao.getObjectById(Organisation.class, body.get(TRANSPORTER));
            if (TransportUtil.setTransporter(transportation, transporter)) {
                save = true;
            }
        }

        TransportCustomer customer = TransportCustomer.valueOf(String.valueOf(body.get(CUSTOMER)));
        if (TransportUtil.setCustomer(transportation, customer)){
            save = true;
        }

        if (save){
            dao.save(transportation.getCreateTime(), transportation);
        }

        if (body.containsKey(PRODUCTS)){
            saveProducts((JSONArray) body.get(PRODUCTS), transportation);
        }

        return transportation;
    }

    private void saveProducts(JSONArray arr, Transportation2 transportation) {

        HashMap<Integer, TransportationDocument> documents = new HashMap<>();
        HashMap<Integer, TransportationProduct> products = new HashMap<>();

        for (TransportationDocument document : transportation.getDocuments()){
            for (TransportationProduct product : document.getProducts()){
                ContractProduct contractProduct = product.getContractProduct();
                Contract contract = contractProduct.getContract();
                documents.put(contract.getId(), document);
                products.put(contractProduct.getId(), product);
            }
        }

        ArrayList<TransportationDocument> saveDocuments = new ArrayList<>();
        ArrayList<TransportationProduct> saveProducts = new ArrayList<>();

        for (Object o : arr){
            JSONObject json = (JSONObject) o;
            ContractProduct contractProduct = dao.getObjectById(ContractProduct.class, json.get(CONTRACT_PRODUCT));
            Contract contract = contractProduct.getContract();

            boolean isNewDocument = false;
            TransportationDocument document;
            if (documents.containsKey(contract.getId())){
                document = documents.remove(contract.getId());
            } else {
                document = new TransportationDocument();
                document.setTransportation(transportation);
                isNewDocument = true;
            }

            if (TransportUtil.setAddress(contract.getAddress(), document) || isNewDocument){
                saveDocuments.add(document);
            }

            document.setAddress(contract.getAddress());

            boolean isNewProduct = false;
            TransportationProduct product;
            if (products.containsKey(contractProduct.getId())){
                product = products.remove(contractProduct.getId());
            } else {
                product = new TransportationProduct();
                product.setDocument(document);
                product.setContractProduct(contractProduct);
                isNewProduct = true;
            }

            Float amount = Float.parseFloat(String.valueOf(json.get(PLAN)));
            if (TransportUtil.setAmount(amount, product) || isNewProduct){
                saveProducts.add(product);
            }

        }
        saveDocuments.forEach(dao::save);
        saveProducts.forEach(dao::save);
        products.values().forEach(dao::remove);
        documents.values().forEach(dao::remove);
        products.clear();
        documents.clear();

    }
}
