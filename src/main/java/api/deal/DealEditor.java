package api.deal;

import constants.Constants;
import entity.DealType;
import entity.Worker;
import entity.deal.DeliveryCost;
import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.documents.Shipper;
import entity.log.comparators.DealComparator;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.transport.ActionTime;
import entity.transport.TransportCustomer;
import entity.transport.Transportation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.DocumentUIDGenerator;
import utils.U;
import utils.UnitBox;
import utils.UpdateUtil;
import utils.hibernate.dao.DealDAO;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by szpt_user045 on 03.03.2020.
 */
public class DealEditor implements Constants {

    public final dbDAO dao = dbDAOService.getDAO();
    private final DealDAO dealDAO = new DealDAO();
    private final DealComparator comparator = new DealComparator();
    private final UpdateUtil updateUtil = new UpdateUtil();

    public Deal editDeal(JSONObject body, Worker creator) {

        boolean save = false;
        boolean isNew = false;

        Deal deal = dealDAO.getDealById(body.get(ID));
        if (deal == null || deal.isArchive()){
            deal = new Deal();
            isNew = true;
        }

        comparator.fix(deal);

//      DEAL PART
        Date date;
        Date dateTo;
        if (body.containsKey(DATE)) {
            date = Date.valueOf(String.valueOf(body.get(DATE)));
        }else if(deal.getDate() != null){
            date = deal.getDate();
        } else {
            date = Date.valueOf(LocalDate.now());
        }

        if (body.containsKey(DATE_TO)) {
            dateTo = Date.valueOf(String.valueOf(body.get(DATE_TO)));
        }else if(deal.getDateTo() != null){
            dateTo = deal.getDateTo();
        } else {
            dateTo = date;
        }

        if (date.after(dateTo)) {
            Date temp = date;
            date = dateTo;
            dateTo = temp;
        }

        if (deal.getDate() == null || !deal.getDate().equals(date)) {
            deal.setDate(date);
            save = true;
        }

        if (deal.getDateTo() == null || !deal.getDateTo().equals(dateTo)) {
            deal.setDateTo(dateTo);
            save = true;
        }

        DealType type = DealType.valueOf(String.valueOf(body.get(TYPE)));
        if (deal.getType() == null || deal.getType() != type) {
            deal.setType(type);
            save = true;
        }

        if (body.containsKey(NUMBER)) {
            String number = String.valueOf(body.get(NUMBER));
            if (!U.exist(deal.getNumber()) || !deal.getNumber().equals(number)) {
                deal.setNumber(number);
                save = true;
            }
        }

        Organisation organisation;
        long organisationId = (long) body.get(COUNTERPARTY);
        if (deal.getOrganisation() == null || deal.getOrganisation().getId() != organisationId) {
            organisation = dao.getOrganisationById(organisationId);
            deal.setOrganisation(organisation);
            save = true;
        }

        final HashMap<Integer, DeliveryCost> costHashMap = new HashMap<>();
        if (deal.getCosts() != null){
            for (DeliveryCost cost : deal.getCosts()){
                costHashMap.put(cost.getId(), cost);
            }
            deal.getCosts().clear();
        } else {
            deal.setCosts(new HashSet<>());
        }
        if (body.containsKey(Constants.COSTS)) {
            for (Object c : (JSONArray) body.get(Constants.COSTS)) {
                JSONObject object = (JSONObject) c;
                final int id = Integer.parseInt(String.valueOf(object.get(ID)));
                DeliveryCost deliveryCost = costHashMap.remove(id);
                if (deliveryCost == null) {
                    deliveryCost = new DeliveryCost();
                    deliveryCost.setDeal(deal);
                }
                TransportCustomer customer = TransportCustomer.valueOf(String.valueOf(object.get(CUSTOMER)));
                deliveryCost.setCustomer(customer);

                float cost = Float.parseFloat(String.valueOf(object.get(COST)));
                deliveryCost.setCost(cost);
                deal.getCosts().add(deliveryCost);
                save = true;
            }
        }

        for (DeliveryCost deliveryCost : costHashMap.values()) {
            dao.remove(deliveryCost);
        }

        if (save) {
            if (isNew) {
                newDeal(deal, creator);
            }

            dao.save(deal);
//            if (!isNew){
//                for (Transportation t : dao.getTransportationByDeal(deal, false, false)){
//                    updateUtil.onSave(t);
//                }
//            }

            try {
                comparator.compare(deal, creator);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        final boolean update = saveDealProducts(deal, (JSONArray) body.get(PRODUCTS), creator);
        if (save || update){
            updateUtil.onSave(deal);
        }

//        JSONArray products = (JSONArray) body.get(PRODUCTS);
//        if (products != null){
//            saveDealProducts(deal, products, creator);
//        }

        return deal;
    }

    public void newDeal(Deal deal, Worker creator) {
        deal.setUid(DocumentUIDGenerator.generateUID());
        ActionTime actionTime = new ActionTime(creator);
        dao.save(actionTime);
        deal.setCreate(actionTime);
    }

    private boolean saveDealProducts(Deal deal, JSONArray array, Worker worker) {
        HashMap<Integer, DealProduct> dealProducts = createProductHashMap(deal.getProducts());
        final Set<DealProduct> products = deal.getProducts();
        final int productCount = products.size();
        products.clear();
        boolean update = false;
        for (Object o : array){
            JSONObject json = (JSONObject) o;

            boolean save = false;
            boolean isNew = false;
            int id = -1;
            if (json.containsKey(ID)){
                id = Integer.parseInt(String.valueOf(json.get(ID)));
            }

            DealProduct dealProduct;
            if (dealProducts.containsKey(id)){
                dealProduct = dealProducts.remove(id);
            } else {
                isNew = true;
                dealProduct = new DealProduct();
                dealProduct.setDeal(deal);
                dealProduct.setUid(DocumentUIDGenerator.generateUID());
            }
            final int productId = Integer.parseInt(String.valueOf(json.get(PRODUCT_ID)));
            if (dealProduct.getProduct() == null || dealProduct.getProduct().getId() != productId){
                Product product = dao.getObjectById(Product.class, productId);
                dealProduct.setProduct(product);
                save = true;
            }

            float quantity = Float.parseFloat(String.valueOf(json.get(QUANTITY)));
            if (dealProduct.getQuantity() != quantity){
                dealProduct.setQuantity(quantity);
                save = true;
            }

            int unitId = Integer.parseInt(String.valueOf(json.get(UNIT_ID)));
            if (dealProduct.getUnit() == null || dealProduct.getUnit().getId() != unitId){
                dealProduct.setUnit(UnitBox.getUnit(unitId));
                save = true;
            }

            float price = Float.parseFloat(String.valueOf(json.get(PRICE)));
            if (dealProduct.getPrice() != price){
                dealProduct.setPrice(price);
                save = true;
            }

            Shipper shipper = dao.getObjectById(Shipper.class, json.get(SHIPPER_ID));
            if (dealProduct.getShipper() == null || dealProduct.getShipper().getId() != shipper.getId()){
                dealProduct.setShipper(shipper);
                save = true;
            }

            if (save){
                if (isNew){
                    ActionTime actionTime = new ActionTime(worker);
                    dao.save(actionTime);
                    dealProduct.setCreate(actionTime);
                }
                dealDAO.saveDealProduct(dealProduct);

                update = true;
            }
            products.add(dealProduct);
        }

        for (DealProduct product : dealProducts.values()){
            if (!dealDAO.removeDealProduct(product)){
                products.add(product);
            }
        }

        if (!update){
            update = productCount != products.size();
        }

        return update;
    }

    private HashMap<Integer, DealProduct> createProductHashMap(Set<DealProduct> products) {
        HashMap<Integer, DealProduct> map = new HashMap<>();
        if (products != null) {
            for (DealProduct product : products) {
                map.put(product.getId(), product);
            }
        }
        return map;
    }
}
