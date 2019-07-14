package api.plan;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import org.json.simple.JSONObject;

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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long id = (long) body.get(Constants.ID);
            LoadPlan loadPlanById = dao.getLoadPlanById(id);
            if(loadPlanById.getTransportation().anyAction()){
                loadPlanById.setCanceled(true);
                dao.save(loadPlanById);
            } else {
                dao.remove(loadPlanById, loadPlanById.getTransportation());
            }

            write(resp, answer);
        }
    }
}
