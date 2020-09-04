package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Modal extends Servlet{
    private static final String MODAL = "/pages/modalWindow.jsp";

    public void show(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(MODAL).forward(req, resp);
    }
}
