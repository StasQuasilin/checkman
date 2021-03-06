package controllers.retail;

import api.sockets.Subscribe;
import constants.Branches;
import controllers.IUIServlet;
import entity.DealType;
import entity.transport.TransportCustomer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 04.12.2019.
 */
@WebServlet(Branches.UI.RETAIL_LIST)
public class RetailList extends IUIServlet {
    private static final String _CONTENT = "/pages/retail/retailList.jsp";
    private static final String _TITLE = "title.retail.list";
    private static final String RETAIL_FILTER = "/pages/filters/retailFilter.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(EDIT, Branches.UI.RETAIL_EDIT);
        req.setAttribute(REMOVE, Branches.UI.RETAIL_REMOVE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(FILTER, RETAIL_FILTER);
        String parameter = req.getParameter(TYPE);
        req.setAttribute(CUSTOMERS, TransportCustomer.values());

        if (parameter != null){
            DealType type = DealType.valueOf(parameter);
            req.setAttribute(TYPE, type);
        } else {
            req.setAttribute(SUBSCRIBE, Subscribe.TRANSPORT);
        }

        show(req, resp);
    }
}
