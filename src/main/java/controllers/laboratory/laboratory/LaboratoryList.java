package controllers.laboratory.laboratory;

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
 * Created by szpt_user045 on 25.03.2019.
 */
@WebServlet(Branches.UI.LABORATORY)
public class LaboratoryList extends IUIServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DealType type = DealType.valueOf(req.getParameter("type"));
        req.setAttribute("title", Constants.Titles.LABORATORY + "." + type.toString());
        req.setAttribute("type", type.toString());
        req.setAttribute("content", "/pages/laboratory/laboratoryList.jsp");
        req.setAttribute("update", Branches.API.LABORATORY_LIST + "?type=" + type.toString());
        req.setAttribute("edit", Branches.UI.LABORATORY_EDIT);
        req.setAttribute("filter", "/pages/filters/transportFilter.jsp");
        show(req, resp);
    }
}
