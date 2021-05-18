package api.deal;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.Deal;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.DealUtil;
import utils.UpdateUtil;
import utils.hibernate.dao.DealDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.DEAL_DELETE)
public class DeleteDealAPI extends ServletAPI{

    final UpdateUtil updateUtil = new UpdateUtil();
    final DealUtil dealUtil = new DealUtil();
    private DealDAO dealDAO = new DealDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            final Deal deal = dealDAO.getDealById(body.get(ID));
            dealUtil.removeDeal(deal);


            write(resp, SUCCESS_ANSWER);
        }
    }
}
