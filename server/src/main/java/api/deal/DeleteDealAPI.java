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
            boolean archive = false;
            for (Transportation plan : list){
                if (plan.any()){
                    archive = true;
                    plan.setArchive(true);
                    dao.save(plan);
                    updateUtil.onArchive(plan);
                } else {
                    dao.remove(plan);
                    updateUtil.onRemove(plan);
                }
            }

            if (deal.getComplete() > 0 || archive){
                deal.setArchive(true);
                dao.save(deal);
                updateUtil.onArchive(deal);
            } else {
                dao.remove(deal);
                updateUtil.onRemove(deal);

            }

            write(resp, SUCCESS_ANSWER);
        }
    }
}
