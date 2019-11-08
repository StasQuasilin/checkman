package controllers.references;

import api.references.organisation.ParseOrganisationServletAPI;
import constants.Branches;
import constants.Constants;
import controllers.IModal;
import org.json.simple.JSONObject;
import utils.U;

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

    public static final String ID = Constants.ID;
    public static final String ORGANISATION = Constants.ORGANISATION;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter(ID);
        JSONObject body = parseBody(req);
        if (!U.exist(id)){
            if (body != null) {
                if (body.containsKey(ID)){
                    id = String.valueOf(body.get(ID));
                }
            }
        }
        if(U.exist(id)){
            req.setAttribute(ORGANISATION, dao.getOrganisationById(Integer.parseInt(id)));
        } else {
            if (body != null) {
                if (body.containsKey(Constants.KEY)){
                    String key = String.valueOf(body.get(Constants.KEY));
                    req.setAttribute(ORGANISATION, ParseOrganisationServletAPI.parseOrganisation(key, dao, getWorker(req)));
                }
            }
        }


        req.setAttribute(TITLE, "references.organisation.edit");
        req.setAttribute(MODAL_CONTENT, "/pages/references/organisationEdit.jsp");
        req.setAttribute(SAVE, Branches.API.References.EDIT_ORGANISATION);
        show(req, resp);
    }
}
