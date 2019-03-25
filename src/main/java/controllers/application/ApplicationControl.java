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
        req.setAttribute("buyList", Branches.UI.DEAL_BUY);
        req.setAttribute("sellList", Branches.UI.DEAL_SELL);
        req.setAttribute("logisticList", Branches.UI.LOGISTIC_LIST);
        req.setAttribute("transportList", Branches.UI.TRANSPORT_LIST);
        req.setAttribute("weightList", Branches.UI.WEIGHT_LIST);
        req.setAttribute("probeList", Branches.UI.PROBE_LIST);
        req.setAttribute("departmentList", Branches.UI.DEPARTMENT_LIST);
        req.setAttribute("laboratoryBuyList", Branches.UI.LABORATORY_BUY);
        req.setAttribute("laboratorySellList", Branches.UI.LABORATORY_SELL);
        req.setAttribute("referencesList", Branches.UI.REFERENCES);
        req.setAttribute("admin", Branches.UI.ADMIN);

        req.getRequestDispatcher("/pages/Application.jsp").forward(req, resp);
    }
}
