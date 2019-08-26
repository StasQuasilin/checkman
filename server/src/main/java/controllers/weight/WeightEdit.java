package controllers.weight;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 19.03.2019.
 */
@WebServlet(Branches.UI.WEIGHT_EDIT)
public class WeightEdit extends IModal {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameterId = req.getParameter(Constants.ID);
        int id = -1;
        if (parameterId != null) {
            id = Integer.parseInt(parameterId);
        }
        if (id != -1) {
            req.setAttribute("plan", dao.getLoadPlanById(id));
        }
        req.setAttribute("title", "title.weight.insert");
        req.setAttribute("saveWeightAPI", Branches.API.SAVE_WEIGHT);
        req.setAttribute("title", Titles.WEIGHT_EDIT);
        req.setAttribute("modalContent", "/pages/weight/weightEdit.jsp");
        req.setAttribute("print", Branches.UI.PRINT_DOCUMENT);
        show(req, resp);
    }
}
