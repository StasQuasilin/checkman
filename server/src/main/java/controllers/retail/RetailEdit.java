package controllers.retail;

import constants.Branches;
import controllers.IModal;
import entity.DealType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 29.11.2019.
 */
@WebServlet(Branches.UI.RETAIL_EDIT)
public class RetailEdit extends IModal {
    private static final String _TITLE = "title.retail.edit";
    private static final String _CONTENT = "/pages/retail/retailEdit.jsp";

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(FIND_DRIVER, Branches.API.References.FIND_DRIVER);
        req.setAttribute(FIND_ORGANISATION, Branches.API.References.FIND_ORGANISATION);
        req.setAttribute(FIND_LOAD_ADDRESS, Branches.API.References.FIND_LOAD_ADDRESS);
        req.setAttribute(FIND_CONTRACTS, Branches.UI.References.FIND_CONTRACTS);
        req.setAttribute(EDIT_ADDRESS, Branches.UI.ADDRESS_EDIT);
        req.setAttribute(EDIT_PRODUCT, Branches.UI.EDIT_PRODUCT);
        req.setAttribute(PARSE_PRODUCT, Branches.API.PARSE_PRODUCT);
        req.setAttribute(FIND_PRODUCT, Branches.API.FIND_PRODUCT);
        req.setAttribute(SAVE, Branches.API.RETAIL_EDIT);
        req.setAttribute(SHIPPERS, dao.getShipperList());
        req.setAttribute(TYPE, DealType.sell);
        req.setAttribute(UNITS, dao.getWeightUnits());
        show(req, resp);
    }
}
