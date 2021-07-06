package api.plan;

import api.ServletAPI;
import constants.Branches;
import entity.Worker;
import entity.transport.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.answers.SuccessAnswer;
import utils.hibernate.dao.DealDAO;
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

    final TransportationEditor transportationEditor = new TransportationEditor();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if(body != null) {
            Worker worker = getWorker(req);
            JSONObject json = (JSONObject) body.get(PLAN);
            Transportation transportation = transportationEditor.saveTransportation(json, worker);

            write(resp, new SuccessAnswer(ID, transportation.getId()));
            body.clear();
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}