package controllers.references.products;

import constants.Branches;
import controllers.IModal;
import entity.products.ProductGroup;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 03.12.2019.
 */
@WebServlet(Branches.UI.References.GROUP_PRODUCT_EDIT)
public class ProductGroupEdit extends IModal {

    private static final String _TITLE = "title.product.group.edit";
    public static final String _CONTENT = "/pages/references/products/productGroupEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            if (body.containsKey(ID)){
                req.setAttribute(PRODUCT, dao.getObjectById(ProductGroup.class, body.get(ID)));
            }
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(SAVE, Branches.API.References.PRODUCT_GROUP_EDIT);
        show(req, resp);
    }
}
