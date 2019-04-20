package controllers.summary;

import constants.Branches;
import constants.Constants;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.SUMMARY_LIST)
public class SummaryController extends IUIServlet {
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("title", Constants.Titles.SUMMARY_LIST);
		req.setAttribute("updateUrl", Branches.API.TRANSPORT_LIST);
		req.setAttribute("showUrl", Branches.UI.SUMMARY_SHOW);
		req.setAttribute("content", "/pages/summary/summaryList.jsp");
		req.setAttribute("filter", "/pages/filters/transportFilter.jsp");
		show(req, resp);
	}
}