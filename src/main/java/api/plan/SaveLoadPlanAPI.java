package api.plan;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.Deal;
import entity.documents.LoadPlan;
import entity.transport.TransportCustomer;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.DateUtil;
import utils.JsonParser;
import utils.PostUtil;
import utils.TransportUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.PLAN_LIST_SAVE)
public class SaveLoadPlanAPI extends IAPI{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        System.out.println(body);
//        LoadPlan loadPlan;
//        boolean save = false;
//
//        if (body.containsKey(body.get(Constants.ID))){}
//        try {
//            int id = Integer.parseInt();
//            loadPlan = hibernator.get(LoadPlan.class, "id", id);
//        } catch (Exception e){
//            loadPlan = new LoadPlan();
//            int dealId = Integer.parseInt(body.get(Constants.DEAL_ID));
//            Deal deal = hibernator.get(Deal.class, "id", dealId);
//            loadPlan.setDeal(deal);
//            loadPlan.setDocumentOrganisation(deal.getDocumentOrganisation());
//            save = true;
//        }
//
//        Date date = DateUtil.parseFromEditor(body.get(Constants.DATE));
//        if (loadPlan.getDate() == null || !loadPlan.getDate().equals(date)){
//            loadPlan.setDate(date);
//            save = true;
//        }
//
//        float plan = Float.parseFloat(body.get(Constants.PLAN));
//        if (loadPlan.getPlan() != plan ){
//            loadPlan.setPlan(plan);
//            save = true;
//        }
//
//        TransportCustomer customer = TransportCustomer.valueOf(body.get(Constants.CUSTOMER));
//        if (loadPlan.getCustomer() != customer){
//            loadPlan.setCustomer(customer);
//            save = true;
//        }
//
//        Transportation transportation;
//        if (loadPlan.getTransportation() == null){
//            transportation= new Transportation();
//            transportation.setCreator(getWorker(req));
//            TransportUtil.saveTransportation(transportation);
//            loadPlan.setTransportation(transportation);
//            save = true;
//        } else {
//            transportation = loadPlan.getTransportation();
//        }
//
//        transportation.setDate(loadPlan.getDate());
//        transportation.setDocumentOrganisation(loadPlan.getDocumentOrganisation());
//
//        if (save){
//            hibernator.save(transportation, loadPlan);
//        }
//
//        PostUtil.write(resp, JsonParser.toJson(new SuccessAnswer()).toJSONString());
    }
}
