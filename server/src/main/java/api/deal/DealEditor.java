package api.deal;

import constants.Constants;
import entity.DealType;
import entity.Worker;
import entity.answers.IAnswer;
import entity.documents.Deal;
import entity.documents.Shipper;
import entity.log.comparators.DealComparator;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
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

/**
 * Created by szpt_user045 on 03.03.2020.
 */
public class DealEditor implements Constants {

    private static final Logger log = Logger.getLogger(DealEditor.class);
    private final dbDAO dao = dbDAOService.getDAO();
    private final DealComparator comparator = new DealComparator();
    private final UpdateUtil updateUtil = new UpdateUtil();

    public Deal editDeal(JSONObject body, Worker worker) {

        log.info(body);
        boolean save = false;
        boolean isNew = false;

        Deal deal = dao.getObjectById(Deal.class, body.get(ID));
        if (deal == null){
            deal = new Deal();
            deal.setCreator(worker);
            isNew = true;
        }

        comparator.fix(deal);

//      DEAL PART

        Date date = Date.valueOf(String.valueOf(body.get(DATE)));
        Date dateTo = Date.valueOf(String.valueOf(body.get(DATE_TO)));
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

        String number = String.valueOf(body.get(NUMBER));
        if (!U.exist(deal.getNumber()) || !deal.getNumber().equals(number)){
            deal.setNumber(number);
            save = true;
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

        Shipper shipper = dao.getShipperById(body.get(REALISATION));
        if (deal.getShipper() == null || deal.getShipper().getId() != shipper.getId()) {
            deal.setShipper(shipper);
            save = true;
        }

        if (save) {
            if (isNew) {
                deal.setUid(DocumentUIDGenerator.generateUID());
                ActionTime actionTime = new ActionTime(worker);
                dao.save(actionTime);
                deal.setCreate(actionTime);
            }
            dao.save(deal);

            try {
                updateUtil.onSave(deal);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                comparator.compare(deal, worker);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return deal;
        }
        return null;
    }
}
