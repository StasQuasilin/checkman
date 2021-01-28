package api.seals;

import api.ServletAPI;
import constants.Branches;
import entity.seals.Seal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 20.02.2020.
 */
@WebServlet(Branches.API.FIND_SEALS)
public class SealFindAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            JSONArray array = pool.getArray();
            for (Seal seal : dao.find(Seal.class, VALUE, String.valueOf(body.get(KEY)))){
                JSONObject object = seal.toJson();
                if (seal.getCargo() != null){
                    object.put(TRANSPORTATION, seal.getCargo().toJson());
                }
                array.add(object);
            }
            JSONObject json = new SuccessAnswer(RESULT, array).toJson();
            write(resp, json.toJSONString());
            pool.put(json);
        }
    }
}
