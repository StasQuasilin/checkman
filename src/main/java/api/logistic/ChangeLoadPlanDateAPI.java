package api.logistic;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import entity.log.comparators.LoadPlanComparator;
import org.json.simple.JSONObject;
import utils.PostUtil;

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
public class ChangeLoadPlanDateAPI extends IAPI {

    private final LoadPlanComparator comparator = new LoadPlanComparator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long id = -1;
            if (body.containsKey(Constants.ID)){
                id = (long) body.get(Constants.ID);
            }
            if (id != -1) {
                LoadPlan plan = hibernator.get(LoadPlan.class, "id", id);
                comparator.fix(plan);
                Date date = Date.valueOf(String.valueOf(body.get(Constants.DATE)));
                plan.setDate(date);
                hibernator.save(plan);
                comparator.compare(plan, getWorker(req));
                write(resp, answer);
            } else {
                write(resp, emptyBody);
            }

        } else {
            write(resp, emptyBody);
        }
    }
}
