package controllers.summary;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;
import entity.DealType;
import entity.Role;
import utils.U;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.SUMMARY_LIST)
public class SummaryList extends IUIServlet {

	final Subscriber[] subscribers = new Subscriber[]{Subscriber.LOAD_PLAN};

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String r = String.valueOf(req.getSession().getAttribute("role"));
		Role role;
		if (U.exist(r)){
			role = Role.valueOf(r);
			if (role == Role.manager) {
				req.setAttribute(EDIT, Branches.UI.SUMMARY_SHOW);
				req.setAttribute("add", Branches.UI.WEIGHT_ADD);
				req.setAttribute("notes", Branches.UI.NOTES_LIST);
				req.setAttribute("archive", Branches.API.ARCHIVE_LOAD_PLAN);
				req.setAttribute("cancel", Branches.UI.WEIGHT_CANCEL);
			}
		}

		req.setAttribute(TITLE, Titles.SUMMARY_LIST);
		req.setAttribute(TYPES, DealType.values());
		req.setAttribute(CONTENT, "/pages/summary/summaryList.jsp");
		req.setAttribute(FILTER, "/pages/filters/transportFilter.jsp");
		req.setAttribute(SUBSCRIBE, subscribers);
		show(req, resp);
	}
}