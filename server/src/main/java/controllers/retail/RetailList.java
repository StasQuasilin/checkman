package controllers.retail;

import constants.Branches;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 29.11.2019.
 */
@WebServlet(Branches.UI.RETAIL_LIST)
public class RetailList extends IUIServlet {
    private static final String _TITLE = "title.retail.list";
    private static final String _CONTENT = "/pages/retail/retailList.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(EDIT, Branches.UI.RETAIL_EDIT);

        show(req, resp);
    }
}
