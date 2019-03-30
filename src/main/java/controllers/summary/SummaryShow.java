package controllers.summary

@WebServlet(Branches.UI.SUMMARY_SHOW)
public class SummaryShow extends IModal{
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setAttribute("title", Constants.Titles.SUMMARY_SHOW);
		req.setAttribute("updateUrl", Branches.API.SUMMARY_SHOW);
		req.setAttribute("modalContent",  "/pages/summary/summaryShow.jsp");
		show(req, resp)
	}
}