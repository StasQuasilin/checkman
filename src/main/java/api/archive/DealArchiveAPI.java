package api.archive;

import api.IAPI;
import constants.Branches;
import entity.DealType;
import entity.documents.Deal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;
import utils.hibernate.DateContainers.LE;

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
public class DealArchiveAPI extends IAPI {
    final JSONArray array = new JSONArray();
    final HashMap<String, Object> parameters = new HashMap<>();
    final LE le = new LE(Date.valueOf(LocalDate.now()));
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);

        DealType type = DealType.valueOf(String.valueOf(body.get("type")));
        parameters.put("archive", true);
        le.setDate(Date.valueOf(LocalDate.now().plusDays(1)));
        parameters.put("date", le);
        parameters.put("type", type);
        List<Deal> deals = hibernator.limitQuery(Deal.class, parameters, 15);

        array.addAll(deals.stream().map(JsonParser::toJson).collect(Collectors.toList()));

        write(resp, array.toJSONString());
        array.clear();
        parameters.clear();
    }
}
