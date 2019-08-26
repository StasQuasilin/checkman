package api.deal;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.Deal;
import entity.documents.LoadPlan;
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
public class DeleteDealServletAPI extends ServletAPI{

    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            final Object id = body.get(Constants.ID);
            final Deal deal = dao.getDealById(id);

            final List<LoadPlan> planList = dao.getLoadPlanByDeal(deal, false, null);

            for (LoadPlan plan : planList){
                dao.remove(plan);
                updateUtil.onRemove(plan);
                dao.remove(plan.getTransportation());
                updateUtil.onRemove(plan.getTransportation());
            }

            if (deal.getComplete() > 0){
                deal.setArchive(true);
                dao.save(deal);
                updateUtil.onArchive(deal);
            } else {
                dao.remove(deal);
                updateUtil.onRemove(deal);
            }

            write(resp, answer);
        }
    }
}
