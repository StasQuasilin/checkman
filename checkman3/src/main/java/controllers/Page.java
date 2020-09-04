package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Page extends Servlet{
    private static final String CONTENT_SHELL = "/pages/contentShell.jsp";

    public void show(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(CONTENT_SHELL).forward(req, resp);
    }
}
