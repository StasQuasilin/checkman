package controllers;

import org.apache.log4j.Logger;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 13.03.2019.
 */
public class IUIServlet extends IServlet {

    private static final String _CONTENT_SHELL = "/pages/contentShell.jsp";
    private final Logger log = Logger.getLogger(IUIServlet.class);
    protected final dbDAO dao = dbDAOService.getDAO();

    public void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute(CONTENT) == null) {
            log.warn("No content for '" + request.getRequestURI() + "'");
        }
        request.getRequestDispatcher(_CONTENT_SHELL).forward(request, response);
    }
}
