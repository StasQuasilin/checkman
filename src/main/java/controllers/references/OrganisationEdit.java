package controllers.references;

import constants.Branches;
import controllers.IModal;
import entity.organisations.Organisation;
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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if(U.exist(id)){
            req.setAttribute("organisation", hibernator.get(Organisation.class, "id", Integer.parseInt(id)));
        }
        req.setAttribute("title", "references.organisation.edit");
        req.setAttribute("modalContent", "/pages/references/organisationEdit.jsp");
        req.setAttribute("save", Branches.API.References.EDIT_ORGANISATION);
        show(req, resp);
    }
}
