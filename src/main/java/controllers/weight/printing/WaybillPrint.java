package controllers.weight.printing;

import api.laboratory.ActNumberService;
import constants.Branches;
import controllers.IModal;
import entity.Role;
import entity.laboratory.transportation.ActType;
import entity.organisations.Organisation;
import entity.transport.Transportation;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 24.04.2019.
 */
@WebServlet(Branches.UI.WAYBILL_PRINT)
public class WaybillPrint extends IModal {

    private static final String _TITLE = "title.waybill.settings";
    private static final String _CONTENT = "/pages/weight/waybillPrintSettings.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if (body != null){

            req.setAttribute(NUMBER, ActNumberService.getActNumber(ActType.waybill));

            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(MODAL_CONTENT, _CONTENT);
            Transportation plan = dao.getObjectById(Transportation.class, body.get(ID));
            req.setAttribute(PRICE, 0);
            req.setAttribute(TRANSPORTATION, plan);
//            Organisation counterparty = plan.getDeal().getOrganisation();
//            req.setAttribute(LEGAL_ADDRESS, dao.getLegalAddress(counterparty));
//            req.setAttribute(LOAD_ADDRESS, dao.getLoadAddress(counterparty));

            req.setAttribute(FIND_ORGANISATION, Branches.API.References.FIND_ORGANISATION);
            req.setAttribute(PARSE_ORGANISATION, Branches.API.References.PARSE_ORGANISATION);
            req.setAttribute(EDIT_ORGANISATION, Branches.UI.References.ORGANISATION_EDIT);

            req.setAttribute(FIND_VEHICLE, Branches.API.References.FIND_VEHICLE);
            req.setAttribute(PARSE_VEHICLE, Branches.API.PARSE_VEHICLE);
            req.setAttribute(EDIT_VEHICLE, Branches.UI.EDIT_VEHICLE);

            req.setAttribute(FIND_DRIVER, Branches.API.References.FIND_DRIVER);
            req.setAttribute(PARSE_DRIVER, Branches.API.PARSE_PERSON);
            req.setAttribute(EDIT_DRIVER, Branches.UI.EDIT_DRIVER);

            req.setAttribute(FIND_TRAILER, Branches.API.References.FIND_TRAILER);
            req.setAttribute(PARSE_TRAILER, Branches.API.PARSE_TRAILER);
			
            req.setAttribute(EDIT_ADDRESS, Branches.UI.ADDRESS_EDIT);
            req.setAttribute(PRINT, Branches.API.WAYBILL_PRINT);
            req.setAttribute(WEIGHTERS, dao.getWorkersByRole(Role.weigher));
            req.setAttribute(SECURITYS, dao.getWorkersByRole(Role.security));
            req.setAttribute(WAREHOUSINGS, dao.getWorkersByRole(Role.warehousing));
            show(req, resp);
        }
    }
}
