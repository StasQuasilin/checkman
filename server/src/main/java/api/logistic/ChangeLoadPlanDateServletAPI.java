package api.logistic;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import entity.log.comparators.LoadPlanComparator;
import org.json.simple.JSONObject;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by szpt_user045 on 25.04.2019.
 */
@WebServlet(Branches.API.CHANGE_DATE)
public class ChangeLoadPlanDateServletAPI extends ServletAPI {

    private static final long serialVersionUID = 3269927702855868553L;
    private final LoadPlanComparator comparator = new LoadPlanComparator();
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            Object id = null;
            if (body.containsKey(Constants.ID)){
                id = body.get(Constants.ID);
            }
            if (id != null) {
                LoadPlan plan = dao.getLoadPlanById(id);
                comparator.fix(plan);
                Date date = Date.valueOf(String.valueOf(body.get(Constants.DATE)));
                plan.setDate(date);
                plan.getTransportation().setDate(date);
                dao.saveLoadPlan(plan);
                dao.save(plan.getTransportation());
                updateUtil.onSave(plan.getTransportation());
                comparator.compare(plan, getWorker(req));
                write(resp, SUCCESS_ANSWER);
            } else {
                write(resp, EMPTY_BODY);
            }

        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
