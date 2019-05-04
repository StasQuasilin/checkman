package api.summary;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import entity.log.Change;
import entity.log.ChangeLog;
import entity.weight.Weight;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.LanguageBase;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@WebServlet(Branches.API.SUMMARY_SHOW)
public class SummaryShowAPI extends IAPI {

	final LanguageBase lb = LanguageBase.getBase();

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
				if (plan.getUid() != null) {
					logs.addAll(hibernator.query(ChangeLog.class, "document", plan.getUid()));
				}
				if (plan.getTransportation() != null) {
					if (plan.getTransportation().getUid() != null) {
						logs.addAll(hibernator.query(ChangeLog.class, "document", plan.getTransportation().getUid()));
					}
					for (Weight weight : plan.getTransportation().getWeights()) {
						logs.addAll(hibernator.query(ChangeLog.class, "document", weight.getUid()));
					}
				}
				//todo logs.addAll(analyses);
				for (Object o : (JSONArray)body.get("logs")){
					if (o != null) {
						long logId = (long) o;
						for (int i = 0; i < logs.size(); i++) {
							if (logs.get(i).getId() == logId) {
								logs.remove(i);
								break;
							}
						}
					}

				}
				String lang = req.getSession().getAttribute("lang").toString();
				for (ChangeLog log : logs){
					log.setLabel(String.format(lb.get(lang, "change." + log.getLabel()), log.getCreator().getValue()));
					for (Change change : log.getChanges()){
						change.setField(String.format(lb.get(lang, "change." + change.getField() + "." + change.getValue()), change.getNewValue(), change.getOldValue()));
					}
				}
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
		