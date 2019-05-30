package controllers.rails;

import constants.Branches;
import controllers.IServlet;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 30.05.2019.
 */
@WebServlet(Branches.UI.RAIL_LIST)
public class RailsList extends IUIServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "title.rails");
        req.setAttribute("content", "/pages/rails/railsList.jsp");
        req.setAttribute("update", Branches.API.RAIL_LIST);
        req.setAttribute("edit", Branches.UI.RAIL_EDIT);
        show(req, resp);
    }
}
