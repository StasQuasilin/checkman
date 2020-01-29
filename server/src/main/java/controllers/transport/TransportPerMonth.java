package controllers.transport;

import constants.Branches;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 29.01.2020.
 */
@WebServlet(Branches.UI.TRANSPORT_PER_MONTH)
public class TransportPerMonth extends IModal {
    private static final String _TITLE = "title.transport.per.month";
    private static final String _CONTENT = "/pages/transport/transportPerMonth.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(PRINT, Branches.API.TRANSPORT_PER_MONTH);
        show(req, resp);
    }
}
