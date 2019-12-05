package controllers.retail;

import api.sockets.Subscriber;
import constants.Branches;
import controllers.IUIServlet;
import entity.DealType;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(EDIT, Branches.UI.RETAIL_EDIT);
        req.setAttribute(CONTENT, _CONTENT);
        String parameter = req.getParameter(TYPE);
        if (parameter != null){
            DealType type = DealType.valueOf(parameter);

        } else {
            req.setAttribute(SUBSCRIBE, Subscriber.TRANSPORT);
        }

        show(req, resp);
    }
}
