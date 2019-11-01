package entity.transport;

import constants.Branches;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 01.11.2019.
 */
@WebServlet(Branches.UI.TRANSPORT_INCOME)
public class TransportIncomePrint extends IModal {
    private static final String _TITLE = "no.title";
    private static final String _CONTENT = "/pages/transport/incomeTransportPrint.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(PRINT, Branches.API.INCOME_TRANSPORT_PRINT);
        show(req, resp);
    }
}
