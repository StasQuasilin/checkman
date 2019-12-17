package controllers.warehousing;

import constants.Branches;
import controllers.IModal;
import entity.storages.Storage;
import org.json.simple.JSONObject;
import utils.storages.StorageStocks;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 16.12.2019.
 */
@WebServlet(Branches.UI.STORAGE_PRODUCT_CORRECTION)
public class StorageProductCorrection extends IModal {

    private static final String _TITLE = "title.product.stock.correction";
    private static final String _CONTENT = "/pages/warehousing/productStockCorrection.jsp";
    final StorageStocks storageStocks = StorageStocks.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null){
            System.out.println(body);
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(STORAGES, dao.getObjects(Storage.class));
        req.setAttribute(STOCKS, storageStocks.getStocks());
        req.setAttribute(SAVE, Branches.API.PRODUCT_STOCK_CORRECTION);

        show(req, resp);
    }
}
