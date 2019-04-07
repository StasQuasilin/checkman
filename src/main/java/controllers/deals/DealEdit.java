package controllers.deals;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import controllers.IServlet;
import entity.DealType;
import entity.Product;
import entity.Worker;
import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.documents.DocumentOrganisation;
import entity.weight.WeightUnit;
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

        if (body.containsKey(Constants.ID)){
            long id = Long.parseLong(String.valueOf(body.get(Constants.ID)));
            req.setAttribute("deal", hibernator.get(Deal.class, "id", id));
            req.setAttribute("title", Constants.Languages.DEAL_EDIT);
            log.info("User \'" + worker.getValue() + "\' open edit deal \'" + id + "\'");
        } else if (body.containsKey(Constants.COPY)){
            long copy = Long.parseLong(String.valueOf(body.get(Constants.COPY)));
            Deal deal = hibernator.get(Deal.class, "id", copy);
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
        req.setAttribute("products", hibernator.query(Product.class, null));
        req.setAttribute("documentOrganisations", hibernator.query(DocumentOrganisation.class, "active", true));
        req.setAttribute("units", hibernator.query(WeightUnit.class, null));
        req.setAttribute("findOrganisation", Branches.API.References.FIND_ORGANISATION);
        req.setAttribute("parseOrganisation", Branches.API.References.PARSE_ORGANISATION);
        req.setAttribute("saveUrl", Branches.API.DEAL_SAVE);
        req.setAttribute("modalContent", "/pages/deals/dealEdit.jsp");
        show(req, resp);
    }
}
