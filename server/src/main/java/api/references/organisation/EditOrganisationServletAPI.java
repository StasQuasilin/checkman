package api.references.organisation;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.answers.IAnswer;
import entity.organisations.Organisation;
import entity.organisations.OrganisationType;
import org.json.simple.JSONObject;
import utils.UpdateUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.EDIT_ORGANISATION)
public class EditOrganisationServletAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            
            Organisation organisation;
            Object id = body.get(Constants.ID);
            if (id != null) {
                organisation = dao.getOrganisationById(id);
            } else {
                organisation = new Organisation();
            }

            String name = String.valueOf(body.get(Constants.NAME));
            name = name.trim().toUpperCase();
            organisation.setName(name);

            String type = String.valueOf(body.get("type"));
            type = type.trim().toUpperCase();
            organisation.setType(type);

            dao.save(organisation);
            IAnswer answer = new SuccessAnswer("organisation", parser.toJson(organisation));
            JSONObject json = parser.toJson(answer);
            write(resp, json.toJSONString());
            pool.put(json);
            updateUtil.onSave(organisation);

            OrganisationType organisationType = dao.getOrganisationTypeByName(type);
            if (organisationType == null) {
                organisationType = new OrganisationType();
                organisationType.setName(type);
                dao.save(organisationType);
            }

        } else {
            write(resp, emptyBody);
        }
    }
}
