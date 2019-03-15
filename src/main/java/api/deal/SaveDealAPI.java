package api.deal;

import api.IAPI;
import api.IChangeAPI;
import constants.Branches;
import constants.Constants;
import entity.IImportantObject;
import entity.Worker;
import entity.documents.Deal;
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
import java.util.List;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.DEAL_SAVE)
public class SaveDealAPI extends IChangeAPI{

    private final DealComparator comparator = new DealComparator();

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
            writeChanges = true;
        }
//        comparator.fix(deal);

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

        Organisation organisation = hibernator.get(Organisation.class, "id", Integer.parseInt(body.get(Constants.ORGANISATION_ID)));
        if (deal.getOrganisation() == null || deal.getOrganisation().getId() != organisation.getId()){
            deal.setOrganisation(organisation);
            writeChanges = true;
        }

        if (writeChanges) {
            hibernator.save(deal);
//            comparator.compare(deal, worker);
        }

        JSONObject json = JsonParser.toJson(deal);
        write(resp, json.toJSONString());

        json.clear();
        body.clear();

    }
}
