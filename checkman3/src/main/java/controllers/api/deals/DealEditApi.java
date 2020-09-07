package controllers.api.deals;

import constants.Apis;
import constants.Keys;
import controllers.api.API;
import entity.deals.*;
import entity.references.Organisation;
import entity.references.Product;
import entity.weight.Unit;
import org.json.simple.JSONArray;
import utils.answer.Answer;
import utils.answer.ErrorAnswer;
import utils.answer.SuccessAnswer;
import utils.constructors.OrganisationConstructor;
import utils.db.dao.DaoService;
import utils.db.dao.deals.DealDAO;
import utils.db.dao.references.OrganisationDAO;
import utils.db.dao.references.ProductDAO;
import utils.db.dao.references.ReferencesDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Set;

@WebServlet(Apis.DEAL_EDIT)
public class DealEditApi extends API {

    private final DealDAO dealDAO = DaoService.getDealDAO();
    private final OrganisationConstructor organisationConstructor = new OrganisationConstructor();
    private final ReferencesDAO referencesDAO = DaoService.getReferencesDAO();
    private final ProductDAO productDAO = DaoService.getProductDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        Answer answer;
        if(body != null){
            System.out.println(body);
            final Object id = body.get(Keys.ID);
            boolean save = false;

            Deal deal = dealDAO.getDealById(id);
            if (deal == null){
                deal = new Deal();
            }

            final String number = body.getString(Keys.NUMBER);
            if (deal.getNumber() == null || !deal.getNumber().equals(number)){
                deal.setNumber(number);
                save = true;
            }

            final String typeString = body.getString(Keys.TYPE);
            if (typeString != null){
                DealType type = DealType.valueOf(typeString);
                if (deal.getType() == null || !deal.getType().equals(type)){
                    deal.setType(type);
                    save = true;
                }
            }

            final Date date = body.getDate(Keys.DATE);
            if (deal.getDate() == null || !deal.getDate().equals(date)){
                deal.setDate(date);
                save = true;
            }

            final Date from = body.getDate(Keys.FROM);
            if (deal.getFrom() == null || !deal.getFrom().equals(from)){
                deal.setFrom(from);
                save = true;
            }

            final Date to = body.getDate(Keys.TO);
            if (deal.getTo() == null || !deal.getTo().equals(to)){
                deal.setTo(to);
                save = true;
            }

            if(saveDocuments((JSONArray)body.get(Keys.DOCUMENTS), deal.getDocuments(), deal)){
                save = true;
            }

            if (save) {
                dealDAO.save(deal);
            }
            answer = new SuccessAnswer();
        } else {
            answer = new ErrorAnswer();
        }

        write(resp, answer);

    }

    private boolean saveDocuments(JSONArray jsonArray, Set<DealDocument> documents, Deal deal) {
        final HashMap<Integer, DealDocument> documentHashMap = createDocumentMap(documents);
        boolean save = false;
        for (Object o : jsonArray){
            final JsonObject documentJson = new JsonObject(o);
            final int documentId = documentJson.getInt(Keys.ID);
            DealDocument dealDocument = documentHashMap.remove(documentId);
            if (dealDocument == null){
                dealDocument = new DealDocument();
                dealDocument.setDeal(deal);
            }


            Organisation organisation = organisationConstructor.getOrganisation(new JsonObject(documentJson.get(Keys.COUNTERPARTY)));
            if (dealDocument.getCounterparty() == null || !dealDocument.getCounterparty().equals(organisation)){
                dealDocument.setCounterparty(organisation);
                save = true;
            }

            dealDAO.save(dealDocument);

            if (saveProducts((JSONArray)documentJson.get(Keys.PRODUCTS), dealDocument.getProducts(), dealDocument)){
                save = true;
            }
        }

        for (DealDocument dealDocument : documentHashMap.values()){
            dealDAO.remove(dealDocument);
            save = true;
        }
        return save;
    }

    private boolean saveProducts(JSONArray jsonArray, Set<DealProduct> products, DealDocument dealDocument) {
        final HashMap<Integer, DealProduct> productMap = createProductMap(products);
        products.clear();
        boolean save = false;
        for (Object o : jsonArray){
            final JsonObject jsonObject = new JsonObject(o);
            final int id = jsonObject.getInt(Keys.ID);
            DealProduct dealProduct = productMap.remove(id);
            if (dealProduct == null){
                dealProduct = new DealProduct();
                dealProduct.setDocument(dealDocument);
            }

            final Product product = productDAO.getProductById(jsonObject.get(Keys.PRODUCT));
            if (dealProduct.getProduct() == null || !dealProduct.getProduct().equals(product)){
                dealProduct.setProduct(product);
                save = true;
            }

            final float amount = jsonObject.getFloat(Keys.AMOUNT);
            if (dealProduct.getAmount() != amount){
                dealProduct.setAmount(amount);
                save = true;
            }

            final Unit unit = referencesDAO.getUnit(jsonObject.get(Keys.UNIT));
            if (dealProduct.getUnit() == null || !dealProduct.getUnit().equals(unit)){
                dealProduct.setUnit(unit);
                save = true;
            }

            final float price = jsonObject.getFloat(Keys.PRICE);
            if (dealProduct.getPrice() != price){
                dealProduct.setPrice(price);
                save = true;
            }

            final Shipper shipper = referencesDAO.getShipper(jsonObject.get(Keys.SHIPPER));
            if (dealProduct.getShipper() == null || !dealProduct.getShipper().equals(shipper)){
                dealProduct.setShipper(shipper);
                save = true;
            }
            dealDAO.save(dealProduct);
        }

        for (DealProduct product : productMap.values()){
            dealDAO.remove(product);
            save = true;
        }
        return save;
    }

    private HashMap<Integer, DealProduct> createProductMap(Set<DealProduct> products) {
        final HashMap<Integer, DealProduct> map = new HashMap<>();
        for (DealProduct product : products){
            map.put(product.getId(), product);
        }
        return map;
    }

    private HashMap<Integer, DealDocument> createDocumentMap(Set<DealDocument> documents) {
        final HashMap<Integer, DealDocument> map = new HashMap<>();
        for (DealDocument dealDocument : documents){
            map.put(dealDocument.getId(), dealDocument);
        }
        return map;
    }
}
