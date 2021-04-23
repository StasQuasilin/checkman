package controllers.warehousing;

import api.sockets.Subscribe;
import constants.Branches;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 10.12.2019.
 */
@WebServlet(Branches.UI.STOP_LIST)
public class StopList extends IUIServlet {

    private static final String _TITLE = "title.stop.list";
    private static final String _CONTENT = "/pages/warehousing/stopList.jsp";
    private static Subscribe[] subscribes = new Subscribe[]{Subscribe.STOP_REPORTS};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(ADD, Branches.UI.STOP_REPORT);
        req.setAttribute(SUBSCRIBE, subscribes);
        show(req, resp);
    }
}
