package controllers.references;

import constants.Apis;
import constants.Urls;
import controllers.Modal;
import entity.analyses.AnalysesType;
import entity.deals.DealType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(Urls.EDIT_PRODUCT)
public class ProductEdit extends Modal {
    private static final String _TITLE = "title.product.edit";
    private static final String _CONTENT = "/pages/references/editProduct.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(TYPES, AnalysesType.values());
        req.setAttribute(ACTIONS, DealType.values());
        req.setAttribute(SAVE, Apis.EDIT_PRODUCT);
        show(req, resp);
    }
}
