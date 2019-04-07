package controllers.seals;

import constants.Branches;
import constants.Constants;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 07.04.2019.
 */
@WebServlet(Branches.UI.SEAL_LIST)
public class SealsList extends IUIServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.SEAL_LIST);
        req.setAttribute("edit", Branches.UI.SEAL_CREATE);
        req.setAttribute("content", "/pages/seals/sealsList.jsp");
        show(req, resp);
    }
}
