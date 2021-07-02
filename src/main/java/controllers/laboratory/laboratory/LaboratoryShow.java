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
@WebServlet(Branches.UI.LABORATORY_SHOW)
public class LaboratoryShow extends IModal {

    final Logger log = Logger.getLogger(LaboratoryShow.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter(Constants.ID));

        Transportation transportation = dao.getObjectById(Transportation.class, id);

        log.info("Show analyses for transportation=" + transportation.getId());

        req.setAttribute("plan", transportation);
        req.setAttribute(PRINT, Branches.UI.LABORATORY_PRINT_OPTIONS);
        AnalysesType analysesType = AnalysesType.other;//transportation.getProduct().getAnalysesType();
        req.setAttribute(TYPE, analysesType.toString());
        switch (analysesType){
            case sun:
                req.setAttribute(MODAL_CONTENT, "/pages/laboratory/sunShow.jsp");
                req.setAttribute(TITLE, Titles.SUN_EDIT);
                break;
            case oil:
                req.setAttribute(MODAL_CONTENT, "/pages/laboratory/oilShow.jsp");
                req.setAttribute(TITLE, Titles.OIL_EDIT);
                break;
            case raf:
                req.setAttribute(MODAL_CONTENT, "/pages/laboratory/rafShow.jsp");
                req.setAttribute(TITLE, Titles.OIL_EDIT);
                break;
            case meal:
                req.setAttribute(MODAL_CONTENT, "/pages/laboratory/cakeShow.jsp");
                req.setAttribute(TITLE, Titles.CAKE_EDIT);
                break;
        }
        if (analysesType != AnalysesType.other) {
            show(req, resp);
        }
    }
}
