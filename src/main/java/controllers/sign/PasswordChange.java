package controllers.sign;

import constants.Branches;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 03.07.2019.
 */
@WebServlet(Branches.UI.CHANGE_PASSWORD)
public class PasswordChange extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "title.change.password");
        req.setAttribute("modalContent", "/pages/personal/changePassword.jsp");
        req.setAttribute("changePassword", Branches.API.CHANGE_PASSWORD);
        show(req, resp);
    }
}
