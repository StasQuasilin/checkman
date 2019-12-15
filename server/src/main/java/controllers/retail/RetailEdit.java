package controllers.retail;

import constants.Branches;
import controllers.IModal;
import entity.DealType;
import entity.transport.TransportCustomer;
import entity.transport.Transportation;
import entity.transport.Transportation2;
import org.json.simple.JSONObject;

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
        JSONObject body = parseBody(req);
        if (body != null){
            Transportation2 transportation = dao.getObjectById(Transportation2.class, body.get(ID));
            req.setAttribute(TRANSPORTATION, transportation);
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(FIND_DRIVER, Branches.API.References.FIND_DRIVER);
        req.setAttribute(EDIT_DRIVER, Branches.UI.EDIT_DRIVER);
        req.setAttribute(PARSE_DRIVER, Branches.API.PARSE_PERSON);
        req.setAttribute(FIND_VEHICLE, Branches.API.References.FIND_VEHICLE);
        req.setAttribute(PARSE_VEHICLE, Branches.API.PARSE_VEHICLE);
        req.setAttribute(EDIT_VEHICLE, Branches.UI.EDIT_VEHICLE);
        req.setAttribute(FIND_TRAILER, Branches.API.References.FIND_TRAILER);
        req.setAttribute(PARSE_TRAILER, Branches.API.PARSE_TRAILER);
        req.setAttribute(FIND_ORGANISATION, Branches.API.References.FIND_ORGANISATION);
        req.setAttribute(EDIT_ORGANISATION, Branches.UI.References.ORGANISATION_EDIT);
        req.setAttribute(PARSE_ORGANISATION, Branches.API.References.PARSE_ORGANISATION);
        req.setAttribute(FIND_LOAD_ADDRESS, Branches.API.References.FIND_LOAD_ADDRESS);
        req.setAttribute(FIND_CONTRACTS, Branches.UI.References.FIND_CONTRACTS);
        req.setAttribute(FIND_PRICE, Branches.API.FIND_PRICE);
        req.setAttribute(EDIT_ADDRESS, Branches.UI.ADDRESS_EDIT);
        req.setAttribute(EDIT_PRODUCT, Branches.UI.EDIT_PRODUCT);
        req.setAttribute(PARSE_PRODUCT, Branches.API.PARSE_PRODUCT);
        req.setAttribute(FIND_PRODUCT, Branches.API.FIND_PRODUCT);
        req.setAttribute(SAVE, Branches.API.RETAIL_EDIT);
        req.setAttribute(SHIPPERS, dao.getShipperList());
        req.setAttribute(TYPE, DealType.sell);
        req.setAttribute(UNITS, dao.getWeightUnits());
        req.setAttribute(CUSTOMERS, TransportCustomer.values());
        req.setAttribute(PRINT, Branches.API.RETAIL_PRINT);
        req.setAttribute(WAYBILL_PRINT, Branches.API.RETAIL_WAYBILL_PRINT);
        show(req, resp);
    }
}
