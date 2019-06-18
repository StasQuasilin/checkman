package api.references.organisation;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.organisations.Organisation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.FIND_ORGANISATION)
public class FindOrganisationAPI extends API {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            String key = String.valueOf(body.get(Constants.KEY));

            HashMap<Integer, Organisation> result = new HashMap<>();
            for (Organisation o : hibernator.find(Organisation.class, "name", key)) {
                result.put(o.getId(), o);
            }
            for (Organisation o : hibernator.find(Organisation.class, "type", key)) {
                result.put(o.getId(), o);
            }

            JSONArray array = result.values().stream().map(JsonParser::toJson).collect(Collectors.toCollection(JSONArray::new));
            write(resp, array.toJSONString());

            array.clear();
            body.clear();
        } else {
            write(resp, emptyBody);
        }
    }
}
