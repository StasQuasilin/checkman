package api.deal;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.Deal;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.UpdateUtil;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            final Deal deal = dao.getObjectById(Deal.class, body.get(ID));

            final List<Transportation> list = dao.getTransportationsByDeal(deal.getId());

            if (deal.getComplete() > 0 || list.size() > 0){
                deal.setArchive(true);
                dao.save(deal);
                updateUtil.onArchive(deal);
                for (Transportation plan : list){
                    plan.setArchive(true);
                    dao.save(plan);
                    updateUtil.onRemove(plan);
                }
            } else {
                dao.remove(deal);
                updateUtil.onRemove(deal);
                for (Transportation plan : list){
                    dao.remove(plan);
                    updateUtil.onRemove(plan);
                }
            }

            write(resp, SUCCESS_ANSWER);
        }
    }
}
