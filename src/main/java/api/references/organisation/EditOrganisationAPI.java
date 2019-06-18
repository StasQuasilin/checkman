package api.references.organisation;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.organisations.Organisation;
import entity.organisations.OrganisationType;
import org.json.simple.JSONObject;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.EDIT_ORGANISATION)
public class EditOrganisationAPI extends API {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            
            Organisation organisation;
            try {
                long id = (long) body.get(Constants.ID);
                organisation = hibernator.get(Organisation.class, "id", id);
            } catch (Exception ignore) {
                organisation = new Organisation();
            }
            String name = String.valueOf(body.get(Constants.NAME));
            name = name.trim().toUpperCase();
            organisation.setName(name);

            String type = String.valueOf(body.get("type"));
            type = type.trim().toUpperCase();
            organisation.setType(type);

            hibernator.save(organisation);
            write(resp, answer);
            OrganisationType organisationType = hibernator.get(OrganisationType.class, "name", type);
            if (organisationType == null) {
                organisationType = new OrganisationType();
                organisationType.setName(type);
                hibernator.save(organisationType);
            }
        } else {
            write(resp, emptyBody);
        }
    }
}
