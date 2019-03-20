package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 14.03.2019.
 */
public class IModal extends IServlet {

    public void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute("modalContent") == null){
            throw new ServletException("field \'modalContent\' required");
        }
        request.getRequestDispatcher("/pages/modalView.jsp").forward(request, response);
    }
}
