package api.deal;

import api.API;
import constants.Branches;
import entity.documents.Deal;
import org.json.simple.JSONObject;
import utils.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 19.04.2019.
 */
@WebServlet(Branches.API.FIND_DEALS)
public class FindDealsAPI extends API {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            List<Deal> deals = dao.getDealsByOrganisation(body.get("organisation"));
            write(resp, JsonParser.toJson(deals).toJSONString());
        } else {
            write(resp, emptyBody);
        }

    }
}
