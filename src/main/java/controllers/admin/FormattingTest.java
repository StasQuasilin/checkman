package controllers.admin;

import constants.Branches;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 24.10.2019.
 */
@WebServlet(Branches.UI.FORMATTING_TEST)
public class FormattingTest extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("api", Branches.API.FORMATTING_TEST);
        req.setAttribute(TITLE, "");
        req.setAttribute(MODAL_CONTENT, "/pages/admin/formattingTest.jsp");
        show(req, resp);
    }
}
