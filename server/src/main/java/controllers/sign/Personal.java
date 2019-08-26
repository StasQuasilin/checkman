package controllers.sign;

import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;
import entity.Role;
import utils.LanguageBase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 15.04.2019.
 */
@WebServlet(Branches.UI.PERSONAL)
public class Personal extends IUIServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Titles.PERSONAL);
        req.setAttribute("content", "/pages/personal/personal.jsp");
        req.setAttribute("uidGenerator", Branches.API.BOT_UID);
        req.setAttribute("botStatus", Branches.API.USER_BOT_SETTINGS);
        req.setAttribute("botSettings", dao.getBotSettingsByWorker(getWorker(req)));
        req.setAttribute("languages", LanguageBase.LANGUAGES);
        req.setAttribute("changeLanguage", Branches.API.CHANGE_LANGUAGE);
        req.setAttribute("changePassword", Branches.UI.CHANGE_PASSWORD);
        req.setAttribute("roles", Role.values());
        req.setAttribute("registration", Branches.API.FRIENDLY_REGISTRATION);
        show(req, resp);
    }
}
