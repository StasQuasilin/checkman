package controllers;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 13.03.2019.
 */
public class IUIServlet extends IServlet {

    private final Logger log = Logger.getLogger(IUIServlet.class);

    public void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute("content") == null) {
            log.warn("No content for \'" + request.getRequestURI() + "\'");
        }
        request.getRequestDispatcher("/pages/contentShell.jsp").forward(request, response);
    }
}
