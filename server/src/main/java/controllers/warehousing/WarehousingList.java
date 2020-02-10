package controllers.warehousing;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;
import controllers.storages.StorageList;
import entity.DealType;
import entity.storages.Storage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kvasik on 07.10.2019.
 */
@WebServlet(Branches.UI.WAREHOUSING_LIST)
public class WarehousingList extends IUIServlet {

    final Subscriber[] subscribers = new Subscriber[]{Subscriber.TRANSPORT_BUY, Subscriber.TRANSPORT_SELL};
//    public static final String _CONTENT = "/pages/warehousing/warehousingList.jsp";
    public static final String _CONTENT = "/pages/weight/weightList.jsp";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, Titles.TRANSPORT_LIST);
        req.setAttribute(SUBSCRIBE, subscribers);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(EDIT, Branches.UI.WAREHOUSING_EDIT);
        req.setAttribute(EDIT_STORAGE, Branches.UI.STORAGE_EDIT);
        req.setAttribute(FILTER, "/pages/filters/transportFilter.jsp");
        req.setAttribute(StorageList.STORAGES, dao.getObjects(Storage.class));
        req.setAttribute(TYPES, DealType.values());
        show(req, resp);
    }
}
