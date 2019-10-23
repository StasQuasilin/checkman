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
        req.setAttribute(TITLE, Titles.PERSONAL);
        req.setAttribute(CONTENT, "/pages/personal/personal.jsp");
        req.setAttribute("languages", LanguageBase.LANGUAGES);
        req.setAttribute("changeLanguage", Branches.API.CHANGE_LANGUAGE);
        req.setAttribute("changePassword", Branches.UI.CHANGE_PASSWORD);
        req.setAttribute("changeOffice", Branches.API.CHANGE_OFFICE);

        show(req, resp);
    }
}
