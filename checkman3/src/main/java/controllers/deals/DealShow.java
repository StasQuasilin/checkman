package controllers.deals;

import constants.Apis;
import constants.Keys;
import constants.Urls;
import controllers.Modal;
import entity.deals.Deal;
import entity.transportations.TransportCustomer;
import utils.db.dao.DaoService;
import utils.db.dao.deals.DealDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Urls.DEAL_SHOW)
public class DealShow extends Modal {

    private static final String _CONTENT = "/pages/deals/dealShow.jsp";
    private static final String _TITLE = "title.deal.show";
    private final DealDAO dealDAO = DaoService.getDealDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            final Object id = body.get(Keys.ID);

            req.setAttribute(Keys.DEAL, id);
            final Deal deal = dealDAO.getDealById(id);
            req.setAttribute(Keys.DEALS, dealDAO.getDealsByType(deal.getType()));
        }
        req.setAttribute(Keys.TITLE, _TITLE);
        req.setAttribute(Keys.CONTENT, _CONTENT);
        req.setAttribute(Keys.CUSTOMERS, TransportCustomer.values());
        req.setAttribute(Keys.DEAL_DETAILS, Apis.DEAL_DETAILS);
        req.setAttribute(Keys.SAVE_TRANSPORTATION, Apis.SAVE_TRANSPORTATION);
        req.setAttribute(Keys.SAVE_CARRIAGE, Apis.SAVE_CARRIAGE);

        show(req, resp);
    }
}
