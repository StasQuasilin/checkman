package controllers.laboratory.standards;

import constants.Branches;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 18.12.2019.
 */
@WebServlet(Branches.UI.PROTOCOL_LIST)
public class ProtocolList extends IUIServlet {
    private static final String _TITLE = "title.laboratory.state.standards";
    private static final String _CONTENT = "/pages/laboratory/standards/standardsList.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(PROTOCOLS, dao.getProtocols());
        req.setAttribute(EDIT, Branches.UI.PROTOCOL_EDIT);
        show(req, resp);
    }
}
