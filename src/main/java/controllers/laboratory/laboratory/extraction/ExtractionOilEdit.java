package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import entity.laboratory.subdivisions.extraction.ExtractionOIl;
import utils.PostUtil;
import utils.U;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 28.03.2019.
 */
@WebServlet(Branches.UI.Extraction.OIL_EDIT)
public class ExtractionOilEdit extends IModal {

    private static final String _CONTENT = "/pages/laboratory/subdivisions/extraction/oilEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ExtractionOIl oil = null;
        String id = req.getParameter(ID);
        if (U.exist(id)){
            oil = dao.getExtractionOilById(Integer.parseInt(id));
        }

        if (oil != null) {
            req.setAttribute(OIL, oil);
        }
        req.setAttribute(TITLE, Titles.EXTRACTION_OIL);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(TURNS, TurnBox.getTurns());
        req.setAttribute(SAVE, Branches.API.EXTRACTION_OIL_EDIT);
        show(req, resp);
    }
}
