package api.references.organisation;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.organisations.Organisation;
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

            JSONArray array = pool.getArray();
            for (Organisation organisation : dao.findOrganisation(key)){
                array.add(organisation.toJson());
            }
            write(resp, array.toJSONString());
            pool.put(array);
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
