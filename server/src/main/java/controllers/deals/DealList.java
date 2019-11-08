package controllers.deals;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;
import entity.DealType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.UI.DEAL_LIST)
public class DealList extends IUIServlet{

    private static final String _CONTENT = "/pages/deals/dealList.jsp";
    private static final String _FILTER = "/pages/filters/dealFilter.jsp";
    
    private static final HashMap<DealType, Subscriber> SUBSCRIBES = new HashMap<>();
    static {
        SUBSCRIBES.put(DealType.buy, Subscriber.DEAL_BUY);
        SUBSCRIBES.put(DealType.sell, Subscriber.DEAL_SELL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DealType type = DealType.valueOf(req.getParameter(TYPE));
        req.setAttribute(TITLE, Titles.DEAL_LIST + '-' + type);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(FILTER, _FILTER);
        req.setAttribute(TYPE, type.toString());
        req.setAttribute(SHOW, Branches.UI.DEAL_SHOW);
        req.setAttribute(EDIT, Branches.UI.DEAL_EDIT + "?type=" + type );
        req.setAttribute(DELETE, Branches.UI.DEAL_DELETE);
        req.setAttribute(SUBSCRIBE, SUBSCRIBES.get(type));
        req.setAttribute(LIMIT, -1);
        show(req, resp);
    }
}
