package controllers.login;

import constants.Apis;
import constants.Urls;
import controllers.Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.FIND_WORKER;

@WebServlet(Urls.LOGIN)
public class Login extends Servlet {
    private static final String LOGIN_PAGE = "/pages/login/login.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(FIND_WORKER, Apis.FIND_WORKER);
        req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
    }
}
