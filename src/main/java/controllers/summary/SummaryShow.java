package controllers.summary;

import constants.Branches;
import constants.Titles;
import controllers.IModal;
import entity.transport.Transportation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {Branches.UI.SUMMARY_SHOW, "/archive/show/summary.j"})
public class SummaryShow extends IModal {

	private static final String _CONTENT = "/pages/summary/summaryShow.jsp";

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute(PLAN, dao.getObjectById(Transportation.class, req.getParameter(ID)));
		req.setAttribute(UPDATE, Branches.API.SUMMARY_SHOW);
		req.setAttribute(TITLE, Titles.SUMMARY_SHOW);
		req.setAttribute(MODAL_CONTENT,  _CONTENT);
		show(req, resp);
	}
}