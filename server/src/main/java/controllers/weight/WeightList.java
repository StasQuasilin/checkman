package controllers.weight;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;
import entity.DealType;
import entity.transport.TransportCustomer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */

@WebServlet(Branches.UI.WEIGHT_LIST)
public class WeightList extends IUIServlet{

    final Subscriber[] subscribers = new Subscriber[]{Subscriber.LOAD_PLAN};
    public static final String _CONTENT = "/pages/weight/weightList.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, Titles.WEIGHT_LIST);
        req.setAttribute(SUBSCRIBE, subscribers);
        req.setAttribute(EDIT, Branches.UI.WEIGHT_EDIT);
        req.setAttribute("add", Branches.UI.WEIGHT_ADD);
        req.setAttribute("notes", Branches.UI.NOTES_LIST);
        req.setAttribute("cancel", Branches.UI.WEIGHT_CANCEL);
        req.setAttribute("archive", Branches.API.ARCHIVE_LOAD_PLAN);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(FILTER, "/pages/filters/transportFilter.jsp");
        req.setAttribute(TYPES, DealType.values());
        req.setAttribute(CUSTOMERS, TransportCustomer.values());
        show(req, resp);
    }
}
