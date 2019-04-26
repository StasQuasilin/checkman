package api.summary;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import entity.log.Change;
import entity.log.ChangeLog;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(Branches.API.SUMMARY_SHOW)
public class SummaryShowAPI extends IAPI {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONObject body = parseBody(req);
		if (body != null) {
			long id = -1;
			if (body.containsKey(Constants.ID)){
				id = (long) body.get(Constants.ID);
			}
			if (id != -1) {
				LoadPlan plan = hibernator.get(LoadPlan.class, "id", id);
				ArrayList<ChangeLog> logs = new ArrayList<>();
				logs.addAll(hibernator.query(ChangeLog.class, "document", plan.getUid()));
				logs.addAll(hibernator.query(ChangeLog.class, "document", plan.getTransportation().getUid()));
				write(resp, JsonParser.toJson(
						plan.getTransportation(),
						logs
				).toJSONString());
			} else {
				write(resp, emptyBody);
			}
		} else {
			write(resp, emptyBody);
		}
	}
}
		