package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import utils.TransportUtil;
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
public class ExtractionTurnProtein extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.EXTRACTION_TURN_PROTEIN);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/extraction/turnProteinEdit.jsp");
        req.setAttribute("turns", TurnBox.getBox().getTurns());
        req.setAttribute("save", Branches.API.EXTRACTION_TURN_PROTEIN_EDIT);
        show(req, resp);
    }
}
