package api.references.organisation;

import api.ServletAPI;
import constants.Branches;
import entity.organisations.OrganisationType;
import org.json.simple.JSONObject;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 03.02.2020.
 */
@WebServlet(Branches.API.FIND_ORGANISATION_TYPE)
public class FindOrganisationTypeAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            String key = String.valueOf(body.get(KEY));
            OrganisationType type = dao.getOrganisationTypeByName(key);
            if (type != null){
                JSONObject json = new SuccessAnswer(RESULT, type.toJson()).toJson();
                write(resp, json.toJSONString());
                pool.put(json);
            } else {
                write(resp, EMPTY_JSON);
            }
        }
    }
}
