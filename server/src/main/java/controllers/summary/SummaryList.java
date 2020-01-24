package controllers.summary;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;
import entity.DealType;
import entity.Role;
import entity.transport.TransportCustomer;
import utils.U;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.SUMMARY_LIST)
public class SummaryList extends IUIServlet {

	final Subscriber[] subscribers = new Subscriber[]{Subscriber.LOAD_PLAN};
	public static final String _CONTENT = "/pages/summary/summaryList.jsp";
	private static final String _STATIC = "/pages/summary/staticCalendar.jsp";
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String r = String.valueOf(req.getSession().getAttribute(ROLE));
		Role role;
		if (U.exist(r)){
			role = Role.valueOf(r);
			if (role == Role.manager || role == Role.admin) {
				req.setAttribute(EDIT, Branches.UI.SUMMARY_SHOW);
				req.setAttribute(ADD, Branches.UI.WEIGHT_ADD);
				req.setAttribute("notes", Branches.UI.NOTES_LIST);
				req.setAttribute("archive", Branches.API.ARCHIVE_LOAD_PLAN);
				req.setAttribute(CANCEL, Branches.UI.WEIGHT_CANCEL);
			}
		}
		req.setAttribute(TRANSPORT_CARRIAGES, Branches.UI.TRANSPORT_CARRIAGES);
		req.setAttribute(PRINT, Branches.UI.SUMMARY_PLAN_PRINT);
		req.setAttribute(TITLE, Titles.SUMMARY_LIST);
		req.setAttribute(TYPES, DealType.values());
		req.setAttribute(CONTENT, _CONTENT);
		req.setAttribute(STATIC_CONTENT, _STATIC);
		req.setAttribute(FILTER, "/pages/filters/transportFilter.jsp");
		req.setAttribute(SUBSCRIBE, subscribers);
		req.setAttribute(CUSTOMERS, TransportCustomer.values());
		show(req, resp);
	}
}