package controllers.admin;

import constants.Branches;
import controllers.IModal;
import entity.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 22.10.2019.
 */
@WebServlet(Branches.UI.USER_REGISTRATION)
public class UserRegistration extends IModal {
    private static final String _TITLE = "title.user.registration";
    private static final String _CONTENT = "/pages/admin/registration.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute("roles", Role.values());
        req.setAttribute("find", Branches.API.References.FIND_WORKER);
        req.setAttribute("registration", Branches.API.SIGN_UP);
        show(req, resp);
    }
}
