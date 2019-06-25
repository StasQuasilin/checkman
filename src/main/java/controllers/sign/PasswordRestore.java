package controllers.sign;

import constants.Branches;
import controllers.IUIServlet;
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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("lang", LanguageBase.getBase().defLang);
        req.setAttribute("restore", Branches.API.PASSWORD_RESTORE);
        req.setAttribute("signIn", Branches.UI.SING_IN);
        req.getRequestDispatcher("/pages/sign/RestorePassword.jsp").forward(req, resp);
    }
}
