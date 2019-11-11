package controllers.deals;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.DealType;
import entity.Worker;
import entity.deal.Contract;
import entity.deal.ContractProduct;
import entity.documents.Deal;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.UI.DEAL_EDIT)
public class DealEdit extends IModal {

    private static final String _CONTENT = "/pages/deals/dealEdit.jsp";
    private final Logger log = Logger.getLogger(DealEdit.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Worker worker = getWorker(req);
        JSONObject body = parseBody(req);
        long id = -1;
        long copy = -1;
        if (body != null) {
            if (body.containsKey(ID)) {
                id = Long.parseLong(String.valueOf(body.get(Constants.ID)));
            } else if (body.containsKey(Constants.COPY)) {
                copy = Long.parseLong(String.valueOf(body.get(Constants.COPY)));
            }
        }

        if (id != -1) {
            req.setAttribute(CONTRACT, dao.getObjectById(Contract.class, id));
            req.setAttribute(TITLE, Constants.Languages.DEAL_EDIT);
            log.info("User \'" + worker.getValue() + "\' edit deal \'" + id + "\'");
        } else if (copy != -1){
            Contract contract = dao.getObjectById(Contract.class, copy);
            contract.setId(-1);
            for (ContractProduct product : contract.getProducts()){
                product.setId(-1);
                product.setDone(0);
            }
            req.setAttribute(CONTRACT, contract);
            req.setAttribute(TITLE, Constants.Languages.DEAL_COPY);
            log.info("User \'" + worker.getValue() + "\' copy deal \'" + copy + "\'");
        } else {
            req.setAttribute(TITLE, Constants.Languages.DEAL_CREATE);
            log.info("User \'" + worker.getValue() + "\' create new deal");
        }

        req.setAttribute(TYPE, req.getParameter("type"));
        req.setAttribute(TYPES, DealType.values());
        req.setAttribute(PRODUCTS, dao.getProductList());
        req.setAttribute(SHIPPERS, dao.getShipperList());
        req.setAttribute(UNITS, dao.getWeightUnits());
        req.setAttribute("findOrganisation", Branches.API.References.FIND_ORGANISATION);
        req.setAttribute("parseOrganisation", Branches.API.References.PARSE_ORGANISATION);
        req.setAttribute("editOrganisation", Branches.UI.References.ORGANISATION_EDIT);
        req.setAttribute(SAVE, Branches.API.DEAL_SAVE);
        req.setAttribute("redirect", Branches.UI.DEAL_SHOW);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        show(req, resp);
    }
}
