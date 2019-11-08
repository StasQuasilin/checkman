package controllers.storages;

import api.sockets.Subscriber;
import constants.Branches;
import controllers.IUIServlet;
import entity.products.Product;
import entity.storages.Storage;
import entity.storages.StorageProduct;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kvasik on 07.10.2019.
 */
@WebServlet(Branches.UI.STORAGE_LIST)
public class StorageList extends IUIServlet {

    private static final String _TITLE = "title.storages";
    private static final String _CONTENT = "/pages/storages/storageList.jsp";
    private static final Subscriber[] SUBSCRIBES = new Subscriber[]{Subscriber.STOCK};
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<Product, ArrayList<Storage>> products = new HashMap<>();
        for (StorageProduct storageProduct : dao.getObjects(StorageProduct.class)){
            Product product = storageProduct.getProduct();
            if (!products.containsKey(product)){
                products.put(product, new ArrayList<>());
            }
            products.get(product).add(storageProduct.getStorage());
        }

        req.setAttribute(PRODUCTS, products);
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute("getStocks", Branches.API.STORAGE_STOCKS);
        req.setAttribute("replace", Branches.UI.STORAGE_PRODUCT_REPLACE);
        req.setAttribute(SUBSCRIBE, SUBSCRIBES);
        show(req, resp);
    }
}
