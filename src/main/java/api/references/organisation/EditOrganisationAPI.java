package api.references.organisation;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.organisations.Organisation;
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
        HashMap<String, String> body = PostUtil.parseBody(req);
        Organisation organisation;
        try {
            int id = Integer.parseInt(body.get(Constants.ID));
            organisation = hibernator.get(Organisation.class, "id", id);
        } catch (Exception ignore){
            organisation = new Organisation();
        }

        organisation.setName(body.get(Constants.NAME));
        hibernator.save(organisation);
    }
}
