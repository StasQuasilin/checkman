package api.deal;

import api.ServletAPI;
import constants.Branches;
import entity.documents.Deal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 19.04.2019.
 */
@WebServlet(Branches.API.FIND_DEALS)
public class FindDealsServletAPI extends ServletAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            List<Deal> deals = dao.getDealsByOrganisation(body.get(ORGANISATION));
            JSONArray array = pool.getArray();
            for (Deal deal : deals){
                array.add(deal.toJson());
            }
            write(resp, array.toJSONString());
            pool.put(array);
        } else {
            write(resp, EMPTY_BODY);
        }

    }
}
