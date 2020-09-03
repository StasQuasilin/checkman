package controllers;

import constants.Urls;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Urls.HOME)
public class ApplicationHome extends Servlet{
    private static final String APPLICATION = "/pages/application.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(APPLICATION).forward(req, resp);
    }
}
