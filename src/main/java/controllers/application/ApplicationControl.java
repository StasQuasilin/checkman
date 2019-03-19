package controllers.application;

import constants.Branches;
import controllers.IServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.UI.APPLICATION)
public class ApplicationControl extends IServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("buy_link", Branches.UI.DEAL_BUY);
        req.setAttribute("sell_link", Branches.UI.DEAL_SELL);
        req.setAttribute("logistic_link", Branches.UI.LOGISTIC_LIST);
        req.setAttribute("transport_link", Branches.UI.TRANSPORT_LIST);
        req.setAttribute("weight_list", Branches.UI.WEIGHT_LIST);
        req.getRequestDispatcher("/pages/application.jsp").forward(req, resp);
    }
}
