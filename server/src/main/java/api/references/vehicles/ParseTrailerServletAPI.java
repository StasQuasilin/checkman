package api.references.vehicles;

import api.ServletAPI;
import constants.Branches;
import entity.transport.Trailer;
import org.json.simple.JSONObject;
import utils.Parser;
import utils.VehicleParser;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 25.11.2019.
 */
@WebServlet(Branches.API.PARSE_TRAILER)
public class ParseTrailerServletAPI extends ServletAPI{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            String key = String.valueOf(body.get("key"));
            String number = Parser.prettyNumber(key);
            Trailer trailer = new Trailer();
            trailer.setNumber(number);
            dao.save(trailer);
            JSONObject json = new SuccessAnswer(RESULT, trailer.toJson()).toJson();
            write(resp, json.toJSONString());
            pool.put(json);
        }
    }
}
