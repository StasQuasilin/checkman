package controllers.storages;

import constants.Branches;
import controllers.IUIServlet;
import entity.storages.Storage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kvasik on 07.10.2019.
 */
@WebServlet(Branches.UI.STORAGE_LIST)
public class StorageList extends IUIServlet {

    public static final String _TITLE = "title.storages";
    public static final String _CONTENT = "/pages/storages/storageList.jsp";
    public static final String STORAGES = "storages";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(STORAGES, dao.getObjects(Storage.class));
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        show(req, resp);
    }
}
