package api.deal;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.documents.Deal;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
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
    final Logger log = Logger.getLogger(DeleteDealAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            final Object id = body.get(Constants.ID);
            final Deal deal = dao.getDealById(id);

            final List<Transportation> list = dao.getTransportationsByDeal(deal.getId());
            boolean any = false;

            for (Transportation transportation : list){
                if (transportation.any()){
                    any = true;
                    break;
                }
            }
            Worker worker = getWorker(req);
            if (any){
                log.info("Archive deal " + deal.getId() + " by " + worker.getValue());
                deal.setArchive(true);
                dao.save(deal);
                for (Transportation transportation : list){
                    log.info("\tTransportation: " + transportation.getId() + " too");
                    transportation.setArchive(true);
                    dao.save(transportation);
                    updateUtil.onArchive(transportation);
                }
                updateUtil.onArchive(deal);
            } else {
                log.info("Remove deal " + deal.getId() + " by " + worker.getValue());
                dao.remove(deal);
                updateUtil.onRemove(deal);
                for (Transportation transportation : list){
                    log.info("\tTransportation: " + transportation.getId() + " too");
                    dao.remove(transportation);
                    updateUtil.onRemove(transportation);
                }
            }

            write(resp, SUCCESS_ANSWER);
        }
    }
}
