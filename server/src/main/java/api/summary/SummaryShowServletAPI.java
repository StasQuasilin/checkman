package api.summary;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import entity.log.Change;
import entity.log.ChangeLog;
import entity.transport.Transportation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.LanguageBase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(Branches.API.SUMMARY_SHOW)
public class SummaryShowServletAPI extends ServletAPI {

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
				Transportation transportation = dao.getObjectById(Transportation.class, id);
				ArrayList<ChangeLog> logs = new ArrayList<>();

				if (transportation != null) {

					if (transportation.getUid() != null) {
						logs.addAll(dao.getLogs(transportation.getUid()));
					}

					if (transportation.getUid() != null) {
						logs.addAll(dao.getLogs(transportation.getUid()));
					}
					if (transportation.getWeight() != null) {
						logs.addAll(dao.getLogs(transportation.getWeight().getUid()));
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
				write(resp, parser.toJson(
						transportation,
						logs
				).toJSONString());
			} else {
				write(resp, EMPTY_BODY);
			}
		} else {
			write(resp, EMPTY_BODY);
		}
	}
}
		