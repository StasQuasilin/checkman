package api.references.organisation;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.organisations.Organisation;
import entity.organisations.OrganisationType;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

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

    dbDAO dao = dbDAOService.getDAO();

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
            write(resp, answer);
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
