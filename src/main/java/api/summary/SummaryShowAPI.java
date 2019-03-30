packege api.summary

@Webservlet(Branches.API.SUMMARY_SHOW)
public class SummaryShowAPI extends IAPI{
	
	@Override
	public void doPost(HttpServlerRequest req, HttpServletResponse resp) throws ServletException, IOException{
		JSONObject body = PostUtil.parseBodyJson(req);
		long id = (long)body.get(Constants,ID);
		int hash = (int)body.get(Constants.HASH);
		LoadPlan plan = hibernator.get(LoadPlan.class, "id", id);
		if (plan.hashCode() != hash){
			write(resp, JsonParser.toLogisticJson(plan).toJSONString());
		} else {
			write(resp, "[]");
		}
	}
}
		