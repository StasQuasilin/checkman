package controllers.transport;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.UI.TRANSPORT_LIST)
public class TransportList extends IUIServlet{
    Subscriber[] subscribers = new Subscriber[]{Subscriber.TRANSPORT_BUY, Subscriber.TRANSPORT_SELL};
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Titles.TRANSPORT_LIST);
        req.setAttribute("edit", Branches.UI.TRANSPORT_SHOW);
        req.setAttribute("content", "/pages/transport/transportList.jsp");
        req.setAttribute("filter", "/pages/filters/transportFilter.jsp");
        req.setAttribute("subscribe", subscribers);
        show(req, resp);
    }
}
