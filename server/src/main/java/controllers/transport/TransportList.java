package controllers.transport;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;
import entity.transport.TransportCustomer;

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
    public static final String _CONTENT = "/pages/transport/transportList.jsp";
    private static final String _FILTER = "/pages/filters/transportFilter.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, Titles.TRANSPORT_LIST);
        req.setAttribute(EDIT, Branches.UI.TRANSPORT_SHOW);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(FILTER, _FILTER);
        req.setAttribute(ADD, Branches.UI.TRANSPORT_ADD_FAST);
        req.setAttribute("printOnTerritory", Branches.UI.PRINT_ON_TERRITORY);
        req.setAttribute("printIncome", Branches.UI.TRANSPORT_INCOME);
        req.setAttribute("transportPerMonth", Branches.UI.TRANSPORT_PER_MONTH);
        req.setAttribute(SUBSCRIBE, subscribers);
        req.setAttribute(HAVE_MENU, false);
        req.setAttribute(CHECK, Branches.UI.CHECK);
        req.setAttribute(CUSTOMERS, TransportCustomer.values());
        show(req, resp);
    }
}
