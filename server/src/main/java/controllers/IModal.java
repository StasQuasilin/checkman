package controllers;

import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 14.03.2019.
 */
public class IModal extends IServlet {

    private static final String PAGE = "/pages/modalView.jsp";
    private static final String ERROR = "Field 'modalContent' or 'content' required";
    protected final dbDAO dao = dbDAOService.getDAO();

    public void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute(MODAL_CONTENT) == null && request.getAttribute(CONTENT) == null){
            throw new ServletException(ERROR);
        }
        request.getRequestDispatcher(PAGE).forward(request, response);
    }
}
