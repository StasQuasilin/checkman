package api.archive;

import api.ServletAPI;
import constants.Branches;
import entity.DealType;
import entity.documents.Deal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 08.04.2019.
 */
@WebServlet(Branches.API.Archive.DEALS)
public class DealArchiveServletAPI extends ServletAPI {
    final JSONArray array = new JSONArray();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            DealType type = DealType.valueOf(String.valueOf(body.get("type")));
            List<Deal> deals = dao.getLimitArchiveDeals(type);

            array.addAll(deals.stream().map(Deal::toJson).collect(Collectors.toList()));

            write(resp, array.toJSONString());
            array.clear();

        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
