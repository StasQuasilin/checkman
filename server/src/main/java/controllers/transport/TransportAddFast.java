package controllers.transport;

import constants.Branches;
import controllers.IModal;
import entity.Role;
import entity.products.ProductAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.TRANSPORT_ADD_FAST)
public class TransportAddFast extends IModal {
    private static final String _TITLE = "title.transport.add";
    private static final String _CONTENT = "/pages/transport/transportAddFast.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(SAVE, Branches.API.TRANSPORTATION_SAVE_FAST);
        req.setAttribute(FIND_ORGANISATION, Branches.API.References.FIND_ORGANISATION);
        req.setAttribute(PARSE_ORGANISATION, Branches.API.References.PARSE_ORGANISATION);
        req.setAttribute(EDIT_ORGANISATION, Branches.UI.References.ORGANISATION_EDIT);
        req.setAttribute(FIND_VEHICLE, Branches.API.References.FIND_VEHICLE);
        req.setAttribute(PARSE_VEHICLE, Branches.API.PARSE_VEHICLE);
        req.setAttribute(EDIT_VEHICLE, Branches.UI.EDIT_VEHICLE);
        req.setAttribute(FIND_DRIVER, Branches.API.References.FIND_DRIVER);
        req.setAttribute(PARSE_DRIVER, Branches.API.PARSE_PERSON);
        req.setAttribute(EDIT_DRIVER, Branches.UI.EDIT_DRIVER);
        req.setAttribute(FIND_TRAILER, Branches.API.References.FIND_TRAILER);
        req.setAttribute(PARSE_TRAILER, Branches.API.PARSE_TRAILER);
        req.setAttribute(PRODUCTS, dao.getProductList());
        req.setAttribute(WORKERS, dao.getWorkersByRole(Role.manager));
        req.setAttribute(FIND_DEALS, Branches.API.FIND_DEALS);
        req.setAttribute(ACTIONS, dao.getObjects(ProductAction.class));
        show(req, resp);
    }
}
