package controllers.contracts;

import constants.Branches;
import controllers.IModal;
import entity.DealType;
import entity.deal.Contract;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 04.12.2019.
 */
@WebServlet(Branches.UI.CONTRACT_EDIT)
public class ContractEdit extends IModal {
    private static final String _TITLE = "title.contract.edit";
    private static final String _CONTENT = "/pages/contracts/contractEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            if (body.containsKey(ID)) {
                Contract contract = dao.getObjectById(Contract.class, body.get(ID));
                req.setAttribute(CONTRACT, contract);
            }
            String parameter = req.getParameter(TYPE);
            DealType type;
            if (parameter != null){
                type = DealType.valueOf(parameter);
            } else {
                type = DealType.sell;
            }
            req.setAttribute(TYPE, type);
            req.setAttribute(FIND_ORGANISATION, Branches.API.References.FIND_ORGANISATION);
            req.setAttribute(FIND_LOAD_ADDRESS, Branches.API.References.FIND_LOAD_ADDRESS);
            req.setAttribute(FIND_PRODUCT, Branches.API.FIND_PRODUCT);
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(MODAL_CONTENT, _CONTENT);
            req.setAttribute(SAVE, Branches.API.EDIT_CONTRACT);
            show(req, resp);
        }
    }
}
