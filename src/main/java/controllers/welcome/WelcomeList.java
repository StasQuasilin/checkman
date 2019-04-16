package controllers.welcome;

import constants.Branches;
import constants.Constants;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 16.04.2019.
 */
@WebServlet(Branches.UI.WELCOME)
public class WelcomeList extends IUIServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.WELCOME);
        req.setAttribute("content", "/pages/welcome/welcome.jsp");
        show(req, resp);
    }
}
