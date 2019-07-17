package controllers.summary;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Constants;
import controllers.IUIServlet;
import entity.DealType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.SUMMARY_LIST)
public class SummaryController extends IUIServlet {

	final Subscriber[] subscribers = new Subscriber[]{Subscriber.LOAD_PLAN};

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("title", Constants.Titles.SUMMARY_LIST);
		req.setAttribute("edit", Branches.UI.SUMMARY_SHOW);
		req.setAttribute("types", DealType.values());
		req.setAttribute("content", "/pages/weight/weightList.jsp");
		req.setAttribute("filter", "/pages/filters/transportFilter.jsp");
		req.setAttribute("subscribe", subscribers);
		show(req, resp);
	}
}