package controllers.laboratory.laboratory;

import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;
import entity.DealType;
import entity.transport.TransportCustomer;

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

    public static final String _CONTENT = "/pages/laboratory/laboratoryList.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DealType type = DealType.valueOf(req.getParameter("type"));
        req.setAttribute(TITLE, Titles.LABORATORY + "." + type.toString());
        req.setAttribute(TYPE, type.toString());
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute("update", Branches.API.LABORATORY_LIST + "?type=" + type.toString());
        req.setAttribute(EDIT, Branches.UI.LABORATORY_EDIT);
        req.setAttribute("print", Branches.UI.LABORATORY_PRINT);
        req.setAttribute(FILTER, "/pages/filters/transportFilter.jsp");
        req.setAttribute(CUSTOMERS, TransportCustomer.values());
        req.setAttribute(SUBSCRIBE, "LABORATORY_" + type.toString().toUpperCase());
        req.setAttribute(HAVE_MENU, false);
        show(req, resp);
    }
}
