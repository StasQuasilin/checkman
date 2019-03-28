package controllers.laboratory.department;

import constants.Branches;
import constants.Constants;
import controllers.IUIServlet;
import entity.Subdivision;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 25.03.2019.
 */
@WebServlet(Branches.UI.DEPARTMENT_LIST)
public class DepartmentList extends IUIServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.DEPARTMENT_LIST);
        req.setAttribute("filter", "/pages/laboratory/department/departmentFilter.jsp");
        req.setAttribute("subdivisions", hibernator.query(Subdivision.class, null));
        show(req, resp);
    }
}
