package api.references.organisation;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.FIND_ORGANISATION)
public class FindOrganisationServletAPI extends ServletAPI {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            String key = String.valueOf(body.get(Constants.KEY));

            JSONArray array = dao.findOrganisation(key).stream().map(parser::toJson).collect(Collectors.toCollection(JSONArray::new));
            write(resp, array.toJSONString());

            array.clear();
            body.clear();
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
