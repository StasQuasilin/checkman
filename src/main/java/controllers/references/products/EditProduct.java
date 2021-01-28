package controllers.references.products;

import constants.Branches;
import controllers.IModal;
import entity.DealType;
import entity.products.Product;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 29.11.2019.
 */
@WebServlet(Branches.UI.EDIT_PRODUCT)
public class EditProduct extends IModal {
    private static final String _TITLE = "title.product.edit";
    private static final String _CONTENT = "/pages/references/products/productEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Product product = dao.getObjectById(Product.class, body.get(ID));
            req.setAttribute(PRODUCT, product);
            req.setAttribute(SETTINGS, dao.getProductSettings(product));
            req.setAttribute(ACTIONS, dao.getProductActionsByProduct(product));
        }
        req.setAttribute(TYPES, DealType.values());
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(SAVE, Branches.API.PRODUCT_EDIT);
        req.setAttribute(FIND_GROUP, Branches.API.References.FIND_PRODUCT_GROUP);
        req.setAttribute(EDIT_GROUP, Branches.UI.References.GROUP_PRODUCT_EDIT);
        req.setAttribute(PARSE_GROUP, Branches.API.References.PARSE_PRODUCT_GROUP);
        req.setAttribute(UNITS, dao.getWeightUnits());
        show(req, resp);
    }
}
