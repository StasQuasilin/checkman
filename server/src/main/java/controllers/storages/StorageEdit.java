package controllers.storages;

import constants.Branches;
import constants.Constants;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kvasik on 07.10.2019.
 */
@WebServlet(Branches.UI.STORAGE_EDIT)
public class StorageEdit extends IModal {

    public static final String _TITLE = "title.storage.edit";
    public static final String _CONTENT = "/pages/storages/storageEdit.jsp";
    private static final long serialVersionUID = 3173904142641540463L;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter(ID));
        req.setAttribute(STORAGE, dao.getStorageById(id));
        req.setAttribute(STORAGE_PRODUCTS, dao.getStorageProductByStorage(id));
        req.setAttribute(PRODUCTS, dao.getProductList());
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(SAVE, Branches.API.STORAGE_EDIT);
        show(req, resp);
    }
}
