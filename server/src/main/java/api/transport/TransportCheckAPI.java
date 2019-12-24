package api.transport;

import api.ServletAPI;
import constants.Branches;
import entity.transport.TruckInfo;
import org.json.simple.JSONObject;
import utils.OpenDataBotAPI;
import utils.TruckInfoUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 24.12.2019.
 */
@WebServlet(Branches.API.CHECK)
public class TransportCheckAPI extends ServletAPI {

    TruckInfoUtil util = new TruckInfoUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            String value = String.valueOf(body.get(VALUE));
            TruckInfo info = util.getInfo(value);
            JSONObject json = new SuccessAnswer(RESULT, info.toJson()).toJson();
            write(resp, json.toJSONString());
            pool.put(json);
        }
    }
}
