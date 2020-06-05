package controllers.reports;

import api.socket.SubscribeType;
import constants.Links;
import controllers.Controller;
import entity.User;
import utils.hibernate.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(Links.REPORTS)
public class ReportList extends Controller {

    private static final String _CONTENT = "/pages/reports/reportList.jsp";
//    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(SHOW, Links.SHOW_REPORT);
        req.setAttribute(SUBSCRIBE, SubscribeType.reports);
        req.setAttribute(CONTENT, _CONTENT);
        show(req, resp);
    }
}
