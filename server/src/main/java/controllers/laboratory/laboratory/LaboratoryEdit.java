package controllers.laboratory.laboratory;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import entity.AnalysesType;
import entity.transport.Transportation;
import org.apache.log4j.Logger;

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

    final Logger log = Logger.getLogger(LaboratoryEdit.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter(Constants.ID));
        log.info("Edit analyses for transportation=" + id);
        Transportation transportation = dao.getTransportationById(id);
        req.setAttribute("plan", transportation);
        req.setAttribute("print", Branches.UI.LABORATORY_PRINT_OPTIONS);
        AnalysesType analysesType = transportation.getProduct().getAnalysesType();
        req.setAttribute(TYPE, analysesType.toString());
        switch (analysesType){
            case sun:
                req.setAttribute(MODAL_CONTENT, "/pages/laboratory/sunEdit.jsp");
                req.setAttribute(TITLE, Titles.SUN_EDIT);
                req.setAttribute(SAVE, Branches.API.LABORATORY_SAVE_SUN);

                break;
            case oil:
                req.setAttribute(MODAL_CONTENT, "/pages/laboratory/oilEdit.jsp");
                req.setAttribute(TITLE, Titles.OIL_EDIT);
                req.setAttribute(SAVE, Branches.API.LABORATORY_SAVE_OIL);

                break;
            case raf:
                req.setAttribute(MODAL_CONTENT, "/pages/laboratory/rafEdit.jsp");
                req.setAttribute(TITLE, Titles.OIL_EDIT);
                req.setAttribute(SAVE, Branches.API.LABORATORY_SAVE_OIL);
                break;
            case meal:
                req.setAttribute(MODAL_CONTENT, "/pages/laboratory/cakeEdit.jsp");
                req.setAttribute(TITLE, Titles.CAKE_EDIT);
                req.setAttribute(SAVE, Branches.API.LABORATORY_SAVE_CAKE);
                break;
        }
        show(req, resp);
    }
}
