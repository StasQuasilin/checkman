package controllers.weight;

import constants.Branches;
import controllers.IModal;
import entity.DealType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.SELECT_COUNTERPARTY)
public class SelectCounterpart extends IModal {

    private static final String _TITLE = "select.counterparty";
    private static final String _CONTENT = "/pages/weight/selectCounterparty.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(FIND_ORGANISATION, Branches.API.References.FIND_ORGANISATION);
        req.setAttribute(PARSE_ORGANISATION, Branches.API.References.PARSE_ORGANISATION);
        req.setAttribute(FIND_DEALS, Branches.API.FIND_DEALS);
        req.setAttribute(TYPES, DealType.values());
        show(req, resp);
    }
}
