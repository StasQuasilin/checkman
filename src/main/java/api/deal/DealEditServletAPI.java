package api.deal;

import api.IChangeServletAPI;
import constants.Branches;
import constants.Constants;
import entity.DealType;
import entity.products.Product;
import entity.Worker;
import entity.documents.Deal;
import entity.documents.DocumentOrganisation;
import entity.log.comparators.DealComparator;
import entity.organisations.Organisation;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.DEAL_SAVE)
public class DealEditServletAPI extends IChangeServletAPI {

    private final DealComparator comparator = new DealComparator();
    private final Logger log = Logger.getLogger(DealEditServletAPI.class);
    private final UpdateBox updateBox = UpdateBox.instance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body);
            Deal deal;
            Worker worker = getWorker(req);
            boolean save = false;

            long id = -1;

            if (body.containsKey(Constants.ID)) {
                id = Long.parseLong(String.valueOf(body.get(Constants.ID)));
            }
            if (id != -1) {
                deal = dao.getDealById(id);
            } else {
                deal = new Deal();
                deal.setCreator(worker);
                deal.setUid(DocumentUIDGenerator.generateUID());
                save = true;
            }

            comparator.fix(deal);

            Date date = Date.valueOf(String.valueOf(body.get(Constants.DATE)));
            Date dateTo = Date.valueOf(String.valueOf(body.get(Constants.DATE_TO)));
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

            DealType type = DealType.valueOf(String.valueOf(body.get(Constants.TYPE)));
            if (deal.getType() == null || deal.getType() != type) {
                deal.setType(type);
                save = true;
            }

            Organisation organisation;
            long organisationId = (long) body.get(Constants.CONTRAGENT);
            if (deal.getOrganisation() == null || deal.getOrganisation().getId() != organisationId) {
                organisation = dao.getOrganisationById(organisationId);
                deal.setOrganisation(organisation);
                save = true;
            }

            Product product = dao.getProductById(body.get(Constants.PRODUCT));
            if (deal.getProduct() == null || deal.getProduct().getId() != product.getId()) {
                deal.setProduct(product);
                save = true;
            }

            float quantity = Float.parseFloat(String.valueOf(body.get(Constants.QUANTITY)));
            if (deal.getQuantity() != quantity) {
                deal.setQuantity(quantity);
                save = true;
            }

            long unit = (long) body.get(Constants.UNIT);
            if (deal.getUnit() == null || deal.getUnit().getId() != unit) {
                deal.setUnit(UnitBox.getUnit(unit));
                save = true;
            }
            float price = Float.parseFloat(String.valueOf(body.get(Constants.PRICE)));
            if (deal.getPrice() != price) {
                deal.setPrice(price);
                save = true;
            }

            DocumentOrganisation dO = dao.getDocumentOrganisationById(body.get(Constants.REALISATION));
            if (deal.getDocumentOrganisation() == null || deal.getDocumentOrganisation().getId() != dO.getId()) {
                deal.setDocumentOrganisation(dO);
                save = true;
            }

            if (save) {
                dao.saveDeal(deal);
                try {
                    comparator.compare(deal, worker);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            write(resp, answer);

            body.clear();
        } else {
            write(resp, emptyBody);
        }

    }
}
