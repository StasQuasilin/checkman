package controllers.logistic;

import constants.Branches;
import constants.Constants;
import controllers.IUIServlet;
import entity.DealType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.UI.LOGISTIC_LIST)
public class LogisticList extends IUIServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.LOGISTIC_LIST);
        req.setAttribute("updateLink", Branches.API.LOGISTIC_LIST);
        req.setAttribute("saveLink", Branches.API.LOGISTIC_SAVE);
        req.setAttribute("dealTypes", DealType.values());
        req.setAttribute("content", "/pages/logistic/logisticList.jsp");
        show(req, resp);
    }
}
