package controllers.contracts;

import constants.Branches;
import controllers.IModal;
import entity.deal.Contract;
import entity.transport.TransportCustomer;
import entity.transport.Transportation2;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 17.01.2020.
 */
@WebServlet(Branches.UI.CONTRACT_SHOW)
public class ContractShow extends IModal {

    private static final String _TITLE = "title.contract.show";
    private static final String _CONTENT = "/pages/contracts/contractShow.jsp";

    private static final TransportCustomer[] customers = new TransportCustomer[]{TransportCustomer.szpt, TransportCustomer.cont};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null){
            if (body.containsKey(ID)){
                Contract contract = dao.getObjectById(Contract.class, body.get(ID));
                req.setAttribute(CONTRACT, contract);
                req.setAttribute(TRANSPORTATIONS, dao.getTransportationsByContract(contract));
                req.setAttribute(CUSTOMERS, customers);
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
                req.setAttribute(EDIT, Branches.UI.CONTRACT_EDIT);
                req.setAttribute(SAVE, Branches.API.SAVE_TRANSPORTATION);
                req.setAttribute(REMOVE, Branches.API.REMOVE_TRANSPORTATION);
                req.setAttribute(TITLE, _TITLE);
                req.setAttribute(MODAL_CONTENT, _CONTENT);
                show(req, resp);
            }
        }
    }
}
