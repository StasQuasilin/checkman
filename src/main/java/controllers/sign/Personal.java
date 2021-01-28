package controllers.sign;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IUIServlet;
import entity.Role;
import entity.Worker;
import utils.LanguageBase;
import utils.hibernate.dao.InterestDAO;

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

    private final InterestDAO interestDAO = new InterestDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, Titles.PERSONAL);
        req.setAttribute(CONTENT, "/pages/personal/personal.jsp");
        req.setAttribute("languages", LanguageBase.LANGUAGES);
        req.setAttribute("changeLanguage", Branches.API.CHANGE_LANGUAGE);
        req.setAttribute("changePassword", Branches.UI.CHANGE_PASSWORD);
        req.setAttribute("changeOffice", Branches.API.CHANGE_OFFICE);
        req.setAttribute(Constants.FIND_ORGANISATION, Branches.API.References.FIND_ORGANISATION);
        req.setAttribute(Constants.INTEREST_SAVE, Branches.API.INTEREST_SAVE);
        final Worker worker = getWorker(req);
        req.setAttribute(Constants.INTERESTS, interestDAO.buildInterestJson(worker));
        show(req, resp);
    }
}
