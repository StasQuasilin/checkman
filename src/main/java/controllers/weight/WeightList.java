package controllers.weight;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Constants;
import controllers.IUIServlet;
import entity.DealType;

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

    final Subscriber[] subscribers =new Subscriber[]{Subscriber.TRANSPORT_BUY, Subscriber.TRANSPORT_SELL};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.WEIGHT_LIST);
        req.setAttribute("subscribe", subscribers);
        req.setAttribute("edit", Branches.UI.WEIGHT_EDIT);
        req.setAttribute("add", Branches.UI.WEIGHT_ADD);
        req.setAttribute("notes", Branches.UI.NOTES_LIST);
        req.setAttribute("cancel", Branches.UI.WEIGHT_CANCEL);
        req.setAttribute("content", "/pages/weight/weightList.jsp");
        req.setAttribute("filter", "/pages/filters/transportFilter.jsp");
        req.setAttribute("types", DealType.values());
        show(req, resp);
    }
}
