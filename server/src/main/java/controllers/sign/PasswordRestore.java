package controllers.sign;

import constants.Branches;
import controllers.IUIServlet;
import entity.Role;
import utils.LanguageBase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 20.06.2019.
 */
@WebServlet(Branches.UI.FORGOT)
public class PasswordRestore extends IUIServlet {
    private static final String ADMINS = "admins";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("lang", LanguageBase.DEFAULT_LANGUAGE);
        req.setAttribute("signIn", Branches.UI.SING_IN);
        req.setAttribute(ADMINS, dao.getWorkersByRole(Role.admin));
        req.getRequestDispatcher("/pages/sign/RestorePassword.jsp").forward(req, resp);
    }
}
