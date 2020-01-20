package api.transport2;

import api.ServletAPI;
import constants.Branches;
import entity.transport.Transportation2;
import org.json.simple.JSONObject;
import utils.answers.SuccessAnswer;
import utils.transport.TransportationSaver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 20.01.2020.
 */
@WebServlet(Branches.API.SAVE_TRANSPORTATION)
public class SaveTransportAPI extends ServletAPI {

    TransportationSaver saver = new TransportationSaver();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);

            Transportation2 transportation = saver.saveTransportation(body, getWorker(req));
            JSONObject json = new SuccessAnswer(ID, transportation.getId()).toJson();
            write(resp, json.toJSONString());
            pool.put(json);
        }
    }
}
