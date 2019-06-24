package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.laboratory.LaboratoryTurn;
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

    dbDAO dao = dbDAOService.getDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.EXTRACTION_TURN_GREASE);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/extraction/turnGreaseEdit.jsp");
        req.setAttribute("turns", TurnBox.getBox().getTurns());
        req.setAttribute("save", Branches.API.EXTRACTION_TURN_GREASE_EDIT);
        show(req, resp);
    }
}
