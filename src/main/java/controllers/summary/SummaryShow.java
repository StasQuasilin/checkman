package controllers.summary;

import constants.Branches;
import constants.Constants;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.SUMMARY_SHOW)
public class SummaryShow extends IModal {
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("title", Constants.Titles.SUMMARY_SHOW);
		req.setAttribute("updateUrl", Branches.API.SUMMARY_SHOW);
		req.setAttribute("modalContent",  "/pages/summary/summaryShow.jsp");
		show(req, resp);
	}
}