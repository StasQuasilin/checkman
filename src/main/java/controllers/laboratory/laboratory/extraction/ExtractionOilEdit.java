package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import utils.TransportUtil;
import utils.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 28.03.2019.
 */
@WebServlet(Branches.UI.Extraction.OIL_EDIT)
public class ExtractionOilEdit extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.EXTRACTION_OIL);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/extraction/oilEdit.jsp");
        req.setAttribute("turns", TurnBox.getBox().getTurns());
        req.setAttribute("laborants", TransportUtil.getLaboratoryPersonal());
        req.setAttribute("save", Branches.API.EXTRACTION_OIL_EDIT);
        show(req, resp);
    }
}