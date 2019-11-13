package api.plan;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.REMOVE_PLAN)
public class DeleteLoadPlanAPI extends ServletAPI{

    final UpdateUtil updateUtil = new UpdateUtil();
    final Logger log = Logger.getLogger(DeleteLoadPlanAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long id = (long) body.get(Constants.ID);
            log.info("Delete load plan " + id);
            LoadPlan loadPlanById = dao.getLoadPlanById(id);
            if (loadPlanById != null) {
                if (loadPlanById.getTransportation().anyAction()) {
                    loadPlanById.setCanceled(true);
                    loadPlanById.getTransportation().setArchive(true);
                    dao.save(loadPlanById);
                    dao.save(loadPlanById.getTransportation());
//                    updateUtil.onRemove(loadPlanById.getTransportation());
//                    updateUtil.onRemove(loadPlanById);
                } else {
//                    updateUtil.onRemove(loadPlanById.getTransportation());
//                    updateUtil.onRemove(loadPlanById);
                    dao.remove(loadPlanById, loadPlanById.getTransportation());
                }
            }

            write(resp, SUCCESS_ANSWER);
        }
    }
}
