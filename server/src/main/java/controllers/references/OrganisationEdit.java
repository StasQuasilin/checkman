package controllers.references;

import constants.Branches;
import controllers.IModal;
import entity.organisations.Organisation;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 18.06.2019.
 */
@WebServlet(Branches.UI.References.ORGANISATION_EDIT)
public class OrganisationEdit extends IModal {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if (body != null) {
            if (body.containsKey(ID)){
                Organisation organisation = dao.getObjectById(Organisation.class, body.get(ID));
                req.setAttribute(ORGANISATION, organisation);
                req.setAttribute(ORGANISATION_TYPE, dao.getOrganisationTypeByName(organisation.getType()));
                req.setAttribute(LEGAL_ADDRESS, dao.getLegalAddress(organisation));
                req.setAttribute(LOAD_ADDRESS, dao.getLoadAddress(organisation));
                req.setAttribute(TITLE, "references.organisation.edit");
                req.setAttribute(MODAL_CONTENT, "/pages/references/organisationEdit.jsp");
                req.setAttribute(EDIT_ADDRESS, Branches.UI.ADDRESS_EDIT);
                req.setAttribute(FIND_ORGANISATION_TYPE, Branches.API.FIND_ORGANISATION_TYPE);
                req.setAttribute(SAVE, Branches.API.References.EDIT_ORGANISATION);
                req.setAttribute(INFO, Branches.API.ORGANISATION_INFO);
                show(req, resp);
            }
        }
    }
}
