package controllers.admin;

import api.sign.SignUpAPI;
import constants.Branches;
import constants.Constants;
import controllers.IUIServlet;
import entity.Role;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 15.04.2019.
 */
@WebServlet(Branches.UI.ADMIN)
public class AdminControl extends IUIServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("title", Constants.Titles.ADMIN);
        req.setAttribute("content", "/pages/admin/admin.jsp");
        req.setAttribute("roles", Role.values());
        req.setAttribute("find", Branches.API.References.FIND_WORKER);
        req.setAttribute("registration", Branches.API.SIGN_UP);
        show(req, resp);

    }
}
