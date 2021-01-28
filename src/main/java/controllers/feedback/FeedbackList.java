package controllers.feedback;

import constants.Branches;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kvasik on 01.07.2019.
 */
@WebServlet(Branches.UI.FEEDBACK)
public class FeedbackList extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "title.feedback");
        req.setAttribute("content", "/pages/feedback/Feedback.jsp");
        show(req, resp);
    }
}
