package controllers.transport;

import constants.Branches;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 24.12.2019.
 */
@WebServlet(Branches.UI.CHECK)
public class TransportCheck extends IModal {
    private static final String _TITLE = "title.transport.check";
    private static final String _CONTENT = "/pages/transport/check.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(CHECK, Branches.API.CHECK);
        show(req, resp);
    }
}
