package controllers.deals;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.DealType;
import entity.Worker;
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

    private final Logger log = Logger.getLogger(DealEdit.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Worker worker = getWorker(req);
        JSONObject body = PostUtil.parseBodyJson(req);
        long id = -1;
        long copy = -1;
        if (body != null) {
            if (body.containsKey(Constants.ID)) {
                id = Long.parseLong(String.valueOf(body.get(Constants.ID)));
            } else if (body.containsKey(Constants.COPY)) {
                copy = Long.parseLong(String.valueOf(body.get(Constants.COPY)));
            }
        }

        if (id != -1) {
            req.setAttribute("deal", dao.getDealById(id));
            req.setAttribute("title", Constants.Languages.DEAL_EDIT);
            log.info("User \'" + worker.getValue() + "\' open edit deal \'" + id + "\'");
        } else if (copy != -1){
            Deal deal = dao.getDealById(copy);
            deal.setId(-1);
            deal.setDone(0);
            req.setAttribute("deal", deal);
            req.setAttribute("title", Constants.Languages.DEAL_COPY);
            log.info("User \'" + worker.getValue() + "\' open copy deal \'" + copy + "\'");
        } else {
            req.setAttribute("title", Constants.Languages.DEAL_CREATE);
            log.info("User \'" + worker.getValue() + "\' open create new deal");
        }

        req.setAttribute("type", req.getParameter("type"));
        req.setAttribute("types", DealType.values());
        req.setAttribute("products", dao.getProductList());
        req.setAttribute("shippers", dao.getShipperList());
        req.setAttribute("units", dao.getWeightUnits());
        req.setAttribute("findOrganisation", Branches.API.References.FIND_ORGANISATION);
        req.setAttribute("parseOrganisation", Branches.API.References.PARSE_ORGANISATION);
        req.setAttribute("save", Branches.API.DEAL_SAVE);
        req.setAttribute("redirect", Branches.UI.DEAL_SHOW);
        req.setAttribute("modalContent", "/pages/deals/dealEdit.jsp");
        show(req, resp);
    }
}
