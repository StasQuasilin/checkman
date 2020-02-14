package controllers.references;

import constants.Branches;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 15.04.2019.
 */
@WebServlet(Branches.UI.REFERENCES)
public class ReferencesList extends IUIServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tab = req.getParameter("tab");
        String title = "";
        if (tab != null) {
            title = "." + tab;
            ReferenceTabs tabs = ReferenceTabs.valueOf(tab);
            switch (tabs) {
                case drivers:
                    req.setAttribute("referenceContent", "driverList.jsp");
                    req.setAttribute("update", Branches.API.References.DRIVER_LIST);
                    req.setAttribute(EDIT, Branches.UI.EDIT_DRIVER);
                    req.setAttribute(COLLAPSE, Branches.UI.DRIVER_COLLAPSE);
                    break;
                case organisations:
                    req.setAttribute("referenceContent", "organisationList.jsp");
                    req.setAttribute("update", Branches.API.References.ORGANISATION_LIST);
                    req.setAttribute(EDIT, Branches.UI.References.ORGANISATION_EDIT);
                    break;
                case products:
                    req.setAttribute("referenceContent", "productList.jsp");
                    req.setAttribute("update", Branches.API.References.PRODUCT_LIST);
                    req.setAttribute(EDIT, Branches.UI.EDIT_PRODUCT);
                    break;
            }
            req.setAttribute("tab", tab);
        }
        req.setAttribute("drivers", Branches.UI.REFERENCES + "?tab=" + ReferenceTabs.drivers.toString());
        req.setAttribute("organisations", Branches.UI.REFERENCES + "?tab=" + ReferenceTabs.organisations.toString());
        req.setAttribute("products", Branches.UI.REFERENCES + "?tab=" + ReferenceTabs.products.toString());
        req.setAttribute("title", "title.references" + title);
        req.setAttribute("content", "/pages/references/references.jsp");
        show(req, resp);
    }
    enum ReferenceTabs{
        drivers,
        organisations,
        products
    }
}
