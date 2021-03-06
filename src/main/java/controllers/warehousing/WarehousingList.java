package controllers.warehousing;

import api.sockets.Subscribe;
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

    final Subscribe[] subscribes = new Subscribe[]{Subscribe.TRANSPORT_BUY, Subscribe.TRANSPORT_SELL};
//    public static final String _CONTENT = "/pages/warehousing/warehousingList.jsp";
    public static final String _CONTENT = "/pages/weight/warehousingList.jsp";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, Titles.TRANSPORT_LIST);
        req.setAttribute(SUBSCRIBE, subscribes);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(SHOW, Branches.UI.WAREHOUSING_EDIT);
        req.setAttribute(EDIT_STORAGE, Branches.UI.STORAGE_EDIT);
        req.setAttribute(FILTER, "/pages/filters/transportFilter.jsp");
        req.setAttribute(StorageList.STORAGES, dao.getObjects(Storage.class));
        req.setAttribute(PRINT, Branches.UI.SUMMARY_PLAN_PRINT);
        req.setAttribute(TRANSPORT_CARRIAGES, Branches.UI.TRANSPORT_CARRIAGES);
        req.setAttribute(TYPES, DealType.values());
        show(req, resp);
    }
}
