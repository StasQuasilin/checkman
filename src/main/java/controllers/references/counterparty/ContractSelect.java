package controllers.references.counterparty;

import constants.Branches;
import controllers.IModal;
import entity.deal.Contract;
import entity.organisations.Organisation;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 04.12.2019.
 */
@WebServlet(Branches.UI.References.FIND_CONTRACTS)
public class ContractSelect extends IModal {

    private static final String _TITLE = "title.contract.select";
    private static final String _CONTENT = "/pages/references/counterparty/contractSelector.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Organisation organisation = dao.getOrganisationById(body.get(COUNTERPARTY));
            List<Contract> contracts = dao.getContractsByOrganisation(organisation);
            if (contracts.size() > 0) {
                req.setAttribute(CONTRACTS, contracts);
                req.setAttribute(TITLE, _TITLE);
                req.setAttribute(MODAL_CONTENT, _CONTENT);
                show(req, resp);
            }
        }
    }
}
