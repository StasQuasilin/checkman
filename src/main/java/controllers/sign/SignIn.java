package controllers.sign;

import constants.Branches;
import controllers.IServlet;
import utils.LanguageBase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.UI.SING_IN)
public class SignIn extends IServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("lang", LanguageBase.getBase().defLang);
        req.setAttribute("context", req.getContextPath());
        req.setAttribute("userApi", Branches.API.References.FIND_WORKER);
        req.setAttribute("loginApi", Branches.API.SIGN_IN);
        req.setAttribute("forgot", Branches.UI.FORGOT);
        req.getRequestDispatcher("/pages/sign/SignIn.jsp").forward(req, resp);
    }
}
