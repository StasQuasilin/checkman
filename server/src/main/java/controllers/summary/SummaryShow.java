package controllers.summary;

import constants.Branches;
import constants.Titles;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {Branches.UI.SUMMARY_SHOW, "/archive/show/summary.j"})
public class SummaryShow extends IModal {
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("plan", dao.getLoadPlanById(Integer.parseInt(req.getParameter("id"))));
		req.setAttribute(TITLE, Titles.SUMMARY_SHOW);
		req.setAttribute("update", Branches.API.SUMMARY_SHOW);
		req.setAttribute(MODAL_CONTENT,  "/pages/summary/summaryShow.jsp");
		show(req, resp);
	}
}