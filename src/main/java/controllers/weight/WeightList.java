package controllers.weight;

import api.sockets.Subscribe;
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

    final Subscribe[] subscribes = new Subscribe[]{Subscribe.TRANSPORT_BUY, Subscribe.TRANSPORT_SELL};
    private static final String _CONTENT = "/pages/weight/weightList.jsp";
    private static final String _FILTER = "/pages/filters/transportFilter.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, Titles.WEIGHT_LIST);
        req.setAttribute(SUBSCRIBE, subscribes);

        req.setAttribute(CANCEL, Branches.UI.WEIGHT_CANCEL);
        req.setAttribute(ARCHIVE, Branches.API.ARCHIVE_LOAD_PLAN);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(FILTER, _FILTER);
        req.setAttribute(TYPES, DealType.values());
        req.setAttribute(CUSTOMERS, TransportCustomer.values());
        req.setAttribute(SHOW, Branches.UI.WEIGHT_EDIT);
        req.setAttribute(EDIT, Branches.UI.WEIGHT_ADD);
        req.setAttribute(REPORT, Branches.UI.EDIT_REPORT);
        req.setAttribute(MANUFACTURE_REPORT, Branches.UI.MANUFACTURE_REPORT_EDIT);
        show(req, resp);
    }
}
