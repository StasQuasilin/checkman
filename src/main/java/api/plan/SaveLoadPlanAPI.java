package api.plan;

import api.ServletAPI;
import constants.Branches;
import entity.Worker;
import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.log.comparators.TransportComparator;
import entity.transport.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.*;
import utils.answers.SuccessAnswer;
import utils.transport.TransportationEditor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.PLAN_LIST_SAVE)
public class SaveLoadPlanAPI extends ServletAPI {

    final Logger log = Logger.getLogger(SaveLoadPlanAPI.class);
    final TransportationEditor transportationEditor = new TransportationEditor();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if(body != null) {
            log.info(body);
            Deal deal = dao.getObjectById(Deal.class, body.get(DEAL));
            DealProduct product = null;
            if (body.containsKey(PRODUCT)){
                product = dao.getObjectById(DealProduct.class, body.get(PRODUCT));
            }
            Worker worker = getWorker(req);

            JSONObject json = (JSONObject) body.get(PLAN);
            Transportation transportation = transportationEditor.saveTransportation(deal, product, json, worker, worker);

            JSONObject toJson = new SuccessAnswer(ID, transportation.getId()).toJson();
            write(resp, toJson.toJSONString());
            pool.put(toJson);
            body.clear();
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}