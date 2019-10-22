package controllers.admin;

import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;
import entity.Role;

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

    private static final long serialVersionUID = 426725708745358802L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute(TITLE, Titles.ADMIN);
        req.setAttribute(CONTENT, "/pages/admin/admin.jsp");

        req.setAttribute("botSettings", Branches.API.BOT_SETTINGS);
        req.setAttribute("botStatus", Branches.API.BOT_STATUS);
        req.setAttribute("userRegistration", Branches.UI.USER_REGISTRATION);
        req.setAttribute("userList", Branches.UI.USER_LIST);
        show(req, resp);

    }
}
