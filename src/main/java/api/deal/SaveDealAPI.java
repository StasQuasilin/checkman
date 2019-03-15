package api.deal;

import api.IAPI;
import api.IChangeAPI;
import api.references.organisation.ParseOrganisationAPI;
import constants.Branches;
import constants.Constants;
import entity.DealType;
import entity.IImportantObject;
import entity.Product;
import entity.Worker;
import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.documents.DocumentOrganisation;
import entity.log.Change;
import entity.log.comparators.DealComparator;
import entity.organisations.Organisation;
import org.json.simple.JSONObject;
import utils.*;
import utils.hibernate.Hibernator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.DEAL_SAVE)
public class SaveDealAPI extends IChangeAPI{

    private final DealComparator comparator = new DealComparator();
    private final DealBox dealBox = DealBox.getBOX();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);

        Deal deal;
        Worker worker = getWorker(req);
        boolean writeChanges = false;

        try {
            int id = Integer.parseInt(body.get(Constants.ID));
            deal = hibernator.get(Deal.class, Constants.ID, id);
        } catch (Exception ignored){
            deal = new Deal();
            deal.setCreator(worker);
            deal.setUid(DocumentUIDGenerator.generateUID());
            writeChanges = true;
        }
        comparator.fix(deal);

        String dateString = body.get(Constants.DATE);
        String dateToString = body.get(Constants.DATE_TO);
        Date date;
        Date dateTo;

        if (U.exist(dateString)){
            date = DateUtil.parseFromEditor(dateString);
        } else {
            date = DateUtil.parseFromEditor(dateToString);
        }

        if (U.exist(dateToString)){
            dateTo = DateUtil.parseFromEditor(dateToString);
        } else {
            dateTo = DateUtil.parseFromEditor(dateString);
        }

        if (deal.getDate() == null || !deal.getDate().equals(date)){
            deal.setDate(date);
            writeChanges = true;
        }

        if (deal.getDateTo() == null || !deal.getDateTo().equals(dateTo)){
            deal.setDateTo(dateTo);
            writeChanges = true;
        }

        DealType type = DealType.valueOf(body.get(Constants.TYPE));
        if (deal.getType() == null || deal.getType() != type){
            deal.setType(type);
            writeChanges = true;
        }

        Organisation organisation;
        try {
            int organisationId = Integer.parseInt(body.get(Constants.ORGANISATION_ID));
            organisation = hibernator.get(Organisation.class, "id", organisationId);
        } catch (Exception ignored){
            organisation = ParseOrganisationAPI.parse(body.get(Constants.ORGANISATION));
            hibernator.save(organisation);
        }

        if (deal.getOrganisation() == null || deal.getOrganisation().getId() != organisation.getId()){
            deal.setOrganisation(organisation);
            writeChanges = true;
        }

        Product product = hibernator.get(Product.class, "id", Integer.parseInt(body.get(Constants.PRODUCT_ID)));
        if (deal.getProduct() == null || deal.getProduct().getId() != product.getId()){
            deal.setProduct(product);
            writeChanges = true;
        }

        float quantity = Float.parseFloat(body.get(Constants.QUANTITY));
        if (deal.getQuantity() != quantity){
            deal.setQuantity(quantity);
            writeChanges = true;
        }

        float price = Float.parseFloat(body.get(Constants.PRICE));
        if(deal.getPrice() != price){
            deal.setPrice(price);
            writeChanges = true;
        }

        DocumentOrganisation dO = hibernator.get(DocumentOrganisation.class, "id", Integer.parseInt(body.get(Constants.VISIBILITY)));
        if (deal.getDocumentOrganisation() == null || deal.getDocumentOrganisation().getId() != dO.getId()){
            deal.setDocumentOrganisation(dO);
            writeChanges = true;
        }

        if (writeChanges) {
            hibernator.save(deal);
            dealBox.update(deal);
            try {
                comparator.compare(deal, worker);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        JSONObject json = JsonParser.toJson(deal);
        write(resp, json.toJSONString());

        json.clear();
        body.clear();

    }
}
