package controllers.weight;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.documents.LoadPlan;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 19.03.2019.
 */
@WebServlet(Branches.UI.WEIGHT_PUT)
public class WeightEdit extends IModal {

    dbDAO dao = dbDAOService.getDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter(Constants.ID));
        req.setAttribute("plan", dao.getLoadPlanById(id));
        req.setAttribute("title", "title.weight.insert");
        req.setAttribute("saveWeightAPI", Branches.API.SAVE_WEIGHT);
        req.setAttribute("title", Constants.Titles.WEIGHT_EDIT);
        req.setAttribute("modalContent", "/pages/weight/weightEdit.jsp");
        req.setAttribute("print", Branches.UI.PRINT_DOCUMENT);
        show(req, resp);
    }
}
