package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import entity.laboratory.subdivisions.extraction.TurnProtein;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.U;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 15.05.2019.
 */
@WebServlet(Branches.UI.Extraction.TURN_PROTEIN)
public class ExtractionTurnProteinEdit extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TurnProtein protein = null;

        String id = req.getParameter("id");
        if (U.exist(id)){
            protein = dao.getTurnProteinById(Integer.parseInt(id));
        } else {
            JSONObject body = PostUtil.parseBodyJson(req);
            if (body != null){
                if (body.containsKey(Constants.ID)){
                    protein = dao.getTurnProteinById(body.get(Constants.ID));
                }
            }
        }

        if (protein != null) {
            req.setAttribute("protein", protein);
        }
        req.setAttribute("title", Titles.EXTRACTION_TURN_PROTEIN);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/extraction/turnProteinEdit.jsp");
        req.setAttribute("turns", TurnBox.getTurns());
        req.setAttribute("save", Branches.API.EXTRACTION_TURN_PROTEIN_EDIT);
        show(req, resp);
    }
}
