package api.deal;

import com.google.gson.JsonObject;
import constants.Constants;
import entity.DealType;
import entity.Worker;
import entity.answers.IAnswer;
import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.documents.Shipper;
import entity.log.comparators.DealComparator;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.transport.ActionTime;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.DocumentUIDGenerator;
import utils.U;
import utils.UnitBox;
import utils.UpdateUtil;
import utils.answers.SuccessAnswer;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by szpt_user045 on 03.03.2020.
 */
public class DealEditor implements Constants {

    private static final Logger log = Logger.getLogger(DealEditor.class);
    public final dbDAO dao = dbDAOService.getDAO();
    private final DealComparator comparator = new DealComparator();
    private final UpdateUtil updateUtil = new UpdateUtil();

    public Deal editDeal(JSONObject body, Worker creator) {

        log.info(body);
        boolean save = false;
        boolean isNew = false;

        Deal deal = dao.getObjectById(Deal.class, body.get(ID));
        if (deal == null){
            deal = new Deal();

            isNew = true;
        }

        comparator.fix(deal);

//      DEAL PART

        Date date = Date.valueOf(String.valueOf(body.get(DATE)));
        Date dateTo;
        if (body.containsKey(DATE_TO)){
            dateTo = Date.valueOf(String.valueOf(body.get(DATE_TO)));
            if (date.after(dateTo)) {
                Date temp = date;
                date = dateTo;
                dateTo = temp;
            }
        } else {
            dateTo = date;
        }

        if (deal.getDate() == null || !deal.getDate().equals(date)) {
            deal.setDate(date);
            save = true;
        }

        if (deal.getDateTo() == null || !deal.getDateTo().equals(dateTo)) {
            deal.setDateTo(dateTo);
            save = true;
        }

        DealType type = DealType.valueOf(String.valueOf(body.get(Constants.TYPE)));
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

        //DEAL PRODUCT PART

        Organisation organisation;
        long organisationId = (long) body.get(COUNTERPARTY);
        if (deal.getOrganisation() == null || deal.getOrganisation().getId() != organisationId) {
            organisation = dao.getOrganisationById(organisationId);
            deal.setOrganisation(organisation);
            save = true;
        }

        Product product = dao.getProductById(body.get(PRODUCT));
        if (deal.getProduct() == null || deal.getProduct().getId() != product.getId()) {
            deal.setProduct(product);
            save = true;
        }

        float quantity = Float.parseFloat(String.valueOf(body.get(Constants.QUANTITY)));
        if (deal.getQuantity() != quantity) {
            deal.setQuantity(quantity);
            save = true;
        }

        long unit = (long) body.get(UNIT);
        if (deal.getUnit() == null || deal.getUnit().getId() != unit) {
            deal.setUnit(UnitBox.getUnit(unit));
            save = true;
        }
        float price = Float.parseFloat(String.valueOf(body.get(PRICE)));
        if (deal.getPrice() != price) {
            deal.setPrice(price);
            save = true;
        }

        Shipper shipper = dao.getShipperById(body.get(SHIPPER));
        if (deal.getShipper() == null || deal.getShipper().getId() != shipper.getId()) {
            deal.setShipper(shipper);
            save = true;
        }

        if (save) {
            if (isNew) {
                newDeal(deal, creator);
            }

            dao.save(deal);
            if (!isNew){
                for (Transportation t : dao.getTransportationByDeal(deal, false, false)){
                    try {
                        updateUtil.onSave(t);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                updateUtil.onSave(deal);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                comparator.compare(deal, creator);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JSONArray products = (JSONArray) body.get(PRODUCTS);
        if (products != null){
            saveDealProducts(deal, products, creator);
        }

        return deal;
    }

    public void newDeal(Deal deal, Worker creator) {
        deal.setUid(DocumentUIDGenerator.generateUID());
        ActionTime actionTime = new ActionTime(creator);
        dao.save(actionTime);
        deal.setCreator(creator);
        deal.setCreate(actionTime);
    }

    private void saveDealProducts(Deal deal, JSONArray array, Worker worker) {
        HashMap<Integer, DealProduct> dealProducts = createProductHashMap(deal.getProducts());
        for (Object o : array){
            JSONObject json = (JSONObject) o;

            boolean save = false;
            boolean isNew = false;
            int productId = Integer.parseInt(String.valueOf(json.get(PRODUCT)));

            DealProduct dealProduct;
            if (dealProducts.containsKey(productId)){
                dealProduct = dealProducts.remove(productId);
            } else {
                isNew = true;
                dealProduct = new DealProduct();
                dealProduct.setDeal(deal);
                dealProduct.setUid(DocumentUIDGenerator.generateUID());
            }

            if (dealProduct.getProduct() == null || dealProduct.getProduct().getId() != productId){
                Product product = dao.getObjectById(Product.class, productId);
                dealProduct.setProduct(product);
            }

            float quantity = Float.parseFloat(String.valueOf(json.get(QUANTITY)));
            if (dealProduct.getQuantity() != quantity){
                dealProduct.setQuantity(quantity);
                save = true;
            }

            int unitId = Integer.parseInt(String.valueOf(json.get(UNIT)));
            if (dealProduct.getUnit() == null || dealProduct.getUnit().getId() != unitId){
                dealProduct.setUnit(UnitBox.getUnit(unitId));
                save = true;
            }

            float price = Float.parseFloat(String.valueOf(json.get(PRICE)));
            if (dealProduct.getPrice() != price){
                dealProduct.setPrice(price);
                save = true;
            }

            Shipper shipper = dao.getObjectById(Shipper.class, json.get(SHIPPER));
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
                dao.save(dealProduct);
            }
        }
    }

    private HashMap<Integer, DealProduct> createProductHashMap(Set<DealProduct> products) {
        HashMap<Integer, DealProduct> map = new HashMap<>();
        if (products != null) {
            for (DealProduct product : products) {
                map.put(product.getProduct().getId(), product);
            }
        }
        return map;
    }
}
