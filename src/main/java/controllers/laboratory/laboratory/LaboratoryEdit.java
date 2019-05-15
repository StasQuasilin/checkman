package controllers.laboratory.laboratory;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.AnalysesType;
import entity.documents.LoadPlan;
import utils.TransportUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@WebServlet(Branches.UI.LABORATORY_EDIT)
public class LaboratoryEdit extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter(Constants.ID));
        LoadPlan loadPlan = hibernator.get(LoadPlan.class, "id", id);
        req.setAttribute("plan", loadPlan);
        AnalysesType analysesType = loadPlan.getDeal().getProduct().getAnalysesType();
        switch (analysesType){
            case sun:
                req.setAttribute("modalContent", "/pages/laboratory/sunEdit.jsp");
                req.setAttribute("title", Constants.Titles.SUN_EDIT);
                req.setAttribute("saveUrl", Branches.API.LABORATORY_SAVE_SUN);
                break;
            case oil:
                req.setAttribute("modalContent", "/pages/laboratory/oilEdit.jsp");
                req.setAttribute("title", Constants.Titles.OIL_EDIT);
                req.setAttribute("saveUrl", Branches.API.LABORATORY_SAVE_OIL);
                break;
            case oilcake:
                req.setAttribute("modalContent", "/pages/laboratory/cakeEdit.jsp");
                req.setAttribute("title", Constants.Titles.CAKE_EDIT);
                req.setAttribute("saveUrl", Branches.API.LABORATORY_SAVE_CAKE);
                break;
        }
        req.setAttribute("laborants", TransportUtil.getLaboratoryPersonal());
        show(req, resp);
    }
}
