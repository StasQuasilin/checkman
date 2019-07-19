package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.laboratory.LaboratoryTurn;
import entity.laboratory.subdivisions.extraction.TurnGrease;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.U;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;
import utils.turns.LaboratoryTurnService;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 16.05.2019.
 */
@WebServlet(Branches.UI.Extraction.TURN_GREASE)
public class ExtractionTurnGrease extends IModal {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TurnGrease grease = null;

        String id = req.getParameter("id");
        if (U.exist(id)){
            grease = dao.getTurnGreaseById(Integer.parseInt(id));
        } else {
            JSONObject body = PostUtil.parseBodyJson(req);
            if (body != null){
                if (body.containsKey(Constants.ID)){
                    grease = dao.getTurnGreaseById(body.get(Constants.ID));
                }
            }
        }

        if (grease != null) {
            req.setAttribute("grease", grease);
        }
        req.setAttribute("title", Constants.Titles.EXTRACTION_TURN_GREASE);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/extraction/turnGreaseEdit.jsp");
        req.setAttribute("turns", TurnBox.getTurns());
        req.setAttribute("save", Branches.API.EXTRACTION_TURN_GREASE_EDIT);
        show(req, resp);
    }
}
