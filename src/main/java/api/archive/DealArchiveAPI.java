package api.archive;

import api.API;
import constants.Branches;
import entity.DealType;
import entity.documents.Deal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;
import utils.hibernate.DateContainers.LE;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 08.04.2019.
 */
@WebServlet(Branches.API.Archive.DEALS)
public class DealArchiveAPI extends API {
    final JSONArray array = new JSONArray();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            DealType type = DealType.valueOf(String.valueOf(body.get("type")));
            List<Deal> deals = dao.getArchiveDeals(type);

            array.addAll(deals.stream().map(JsonParser::toJson).collect(Collectors.toList()));

            write(resp, array.toJSONString());
            array.clear();

        } else {
            write(resp, emptyBody);
        }
    }
}
