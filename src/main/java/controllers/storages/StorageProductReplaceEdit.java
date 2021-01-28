package controllers.storages;

import constants.Branches;
import controllers.IModal;
import entity.storages.StorageProduct;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 06.11.2019.
 */
@WebServlet(Branches.UI.STORAGE_PRODUCT_REPLACE)
public class StorageProductReplaceEdit extends IModal {

    private static final String _TITLE = "title.storage.product.replace";
    private static final String _CONTENT = "/pages/storages/storageProductReplace.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(STORAGE_PRODUCTS, dao.getObjects(StorageProduct.class));
        req.setAttribute(SAVE, Branches.API.STORAGE_PRODUCT_REPLACE);
        req.setAttribute(SHIPPERS, dao.getShipperList());
        show(req, resp);
    }
}
