package api.summary;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.API.SUMMARY_SHOW)
public class SummaryShowAPI extends IAPI {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONObject body = PostUtil.parseBodyJson(req);
		long id = (long)body.get(Constants.ID);
		int hash = (int)body.get(Constants.HASH);
		LoadPlan plan = hibernator.get(LoadPlan.class, "id", id);
		if (plan.hashCode() != hash){
			write(resp, JsonParser.toLogisticJson(plan).toJSONString());
		} else {
			write(resp, "[]");
		}
	}
}
		