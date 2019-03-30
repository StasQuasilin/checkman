package controllers.summary
@WebServlet(Branches.UI.SUMMARY_LIST)
public class SummaryControl extends IUIServlet {
	@Override
	public void doPost(HttpServletRequest req, HttpServletRecponce resp) throws ServletException,  IOException{
			req.setAttribute("title", Constants.Titles.SUMMARY_LIST);
			req.setAttribute("updateUrl", Branches.API.TRANSPORT_LIST);
			req.setAttribute("showUrl", Branches.UI.SUMMARY_SHOW);
			req.setAttribute("content", "/pages/summary/summaryList.jsp");
			show(req, resp);
		}
}