package api.archive;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.TransportUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kvasik on 26.08.2019.
 */
@WebServlet(Branches.API.ARCHIVE_LOAD_PLAN)
public class ArchiveLoadPlan extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Object id = body.get(Constants.ID);
            LoadPlan plan = dao.getLoadPlanById(id);
            Transportation transportation = plan.getTransportation();
            if (transportation.isDone() && !transportation.isArchive()){
                TransportUtil.archive(plan);
            }
        }
    }
}
