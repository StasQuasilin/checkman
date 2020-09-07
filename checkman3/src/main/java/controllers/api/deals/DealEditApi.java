package controllers.api.deals;

import constants.Apis;
import constants.Keys;
import controllers.api.API;
import entity.deals.Deal;
import utils.answer.Answer;
import utils.answer.ErrorAnswer;
import utils.answer.SuccessAnswer;
import utils.db.dao.DaoService;
import utils.db.dao.deals.DealDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Apis.DEAL_EDIT)
public class DealEditApi extends API {

    private final DealDAO dealDAO = DaoService.getDealDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        Answer answer;
        if(body != null){
            final Object id = body.get(Keys.ID);
            Deal deal = dealDAO.getDealById(id);
            if (deal == null){
                deal = new Deal();
            }


            dealDAO.save(deal);
            answer = new SuccessAnswer();
        } else {
            answer = new ErrorAnswer();
        }

        write(resp, answer);

    }
}
