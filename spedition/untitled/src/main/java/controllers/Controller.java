package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Controller extends HttpServlet {
    private static final String CONTENT_SHELL = "/pages/contentShell.jsp";
    public void show(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        req.getRequestDispatcher(CONTENT_SHELL).forward(req, response);
    }
}
