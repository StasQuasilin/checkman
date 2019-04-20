package controllers.sign;

import bot.BotSettings;
import constants.Branches;
import constants.Constants;
import controllers.IUIServlet;
import entity.bot.UserBotSetting;
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
        req.setAttribute("title", Constants.Titles.PERSONAL);
        req.setAttribute("content", "/pages/personal/personal.jsp");
        req.setAttribute("changePassword", Branches.API.CHANGE_PASSWORD);
        req.setAttribute("uidGenerator", Branches.API.BOT_UID);
        req.setAttribute("botStatus", Branches.API.BOT_SETTINGS);
        req.setAttribute("botSettings", hibernator.get(UserBotSetting.class, "worker", getWorker(req).getId()));
        req.setAttribute("languages", LanguageBase.getBase().languages);
        show(req, resp);
    }
}
