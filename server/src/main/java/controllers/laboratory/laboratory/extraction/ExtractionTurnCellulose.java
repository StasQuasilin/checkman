package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import controllers.IModal;
import entity.laboratory.subdivisions.extraction.TurnCellulose;
import org.json.simple.JSONObject;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 30.10.2019.
 */
@WebServlet(Branches.UI.Extraction.TURN_CELLULOSE)
public class ExtractionTurnCellulose extends IModal {

    private static final String _TITLE = "title.extraction.turn.cellulose";
    private static final String _CONTENT = "/pages/laboratory/subdivisions/extraction/turnCelluloseEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            if(body.containsKey(ID)){
                req.setAttribute(PROTEIN, dao.getObjectById(TurnCellulose.class, body.get(ID)));
            }
        }

        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(TURNS, TurnBox.getTurns());
        req.setAttribute(SAVE, Branches.API.EXTRACTION_TURN_CELLULOSE);
        show(req, resp);
    }
}
