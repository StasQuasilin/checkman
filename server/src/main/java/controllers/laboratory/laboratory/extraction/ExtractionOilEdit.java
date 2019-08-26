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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ExtractionOIl oil = null;
        String id = req.getParameter("id");
        if (U.exist(id)){
            oil = dao.getExtractionOilById(Integer.parseInt(id));
        } else {
            HashMap<String, String> body = PostUtil.parseBody(req);
            if (body != null){
                if (body.containsKey(Constants.ID)){
                    oil = dao.getExtractionOilById(body.get(Constants.ID));
                }
            }
        }

        if (oil != null) {
            req.setAttribute("oil", oil);
        }
        req.setAttribute("title", Titles.EXTRACTION_OIL);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/extraction/oilEdit.jsp");
        req.setAttribute("turns", TurnBox.getTurns());
        req.setAttribute("save", Branches.API.EXTRACTION_OIL_EDIT);
        show(req, resp);
    }
}
