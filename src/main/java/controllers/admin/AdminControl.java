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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute(TITLE, Titles.ADMIN);
        req.setAttribute(CONTENT, "/pages/admin/admin.jsp");

        req.setAttribute("uidGenerator", Branches.API.BOT_UID);
        req.setAttribute("botStatus", Branches.API.USER_BOT_SETTINGS);
        req.setAttribute("botDelete", Branches.API.USER_BOT_DELETE);
        req.setAttribute("botSettings", dao.getBotSettingsByWorker(getWorker(req)));

        req.setAttribute("userRegistration", Branches.UI.USER_REGISTRATION);
        req.setAttribute("userList", Branches.UI.USER_LIST);
        req.setAttribute("organisationCollapse", Branches.UI.ORGANISATION_COLLAPSE);
        req.setAttribute("formattingTest", Branches.UI.FORMATTING_TEST);

        show(req, resp);

    }
}
