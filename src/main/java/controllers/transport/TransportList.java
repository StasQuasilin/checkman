package controllers.transport;

import constants.Branches;
import constants.Constants;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.UI.TRANSPORT_LIST)
public class TransportList extends IUIServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.TRANSPORT_LIST);
        req.setAttribute("updateLink", Branches.API.TRANSPORT_UPDATE);
        req.setAttribute("showLink", Branches.UI.TRANSPORT_SHOW);
        req.setAttribute("content", "/pages/transport/transportList.jsp");
        show(req, resp);
    }
}
