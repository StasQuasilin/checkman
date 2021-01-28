package controllers.admin;

import constants.Branches;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 24.10.2019.
 */
@WebServlet(Branches.UI.ORGANISATION_COLLAPSE)
public class OrganisationCollapse extends IModal {

    private static final String FIND_ORGANISATION = "find";
    private static final String GET_DEALS = "deals";
    private static final String COLLAPSE = "collapse";
    private static final String _CONTENT = "/pages/admin/organisationCollapse.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, "title.organisation.collapse");
        req.setAttribute(FIND_ORGANISATION, Branches.API.References.FIND_ORGANISATION);
        req.setAttribute(GET_DEALS, Branches.API.FIND_DEALS);
        req.setAttribute(COLLAPSE, Branches.API.ORGANISATION_COLLAPSE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        show(req, resp);
    }
}
