package api.references.organisation;

import api.ServletAPI;
import constants.Branches;
import entity.organisations.Organisation;
import org.json.simple.JSONObject;
import utils.OrganisationInfoUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 06.02.2020.
 */
@WebServlet(Branches.API.ORGANISATION_INFO)
public class OrganisationInfoAPI extends ServletAPI {

    private final OrganisationInfoUtil infoUtil = new OrganisationInfoUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Organisation organisation = dao.getObjectById(Organisation.class, body.get(ID));
            if (infoUtil.checkOrganisation(organisation, getWorker(req))){
                JSONObject json = new SuccessAnswer(RESULT, organisation.toJson()).toJson();
                write(resp, json.toJSONString());
                pool.put(json);
            } else {
                write(resp, SUCCESS_ANSWER);
            }
        }
    }
}
