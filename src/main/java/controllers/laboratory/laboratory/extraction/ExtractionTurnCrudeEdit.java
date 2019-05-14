package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import controllers.IUIServlet;
import utils.TransportUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 14.05.2019.
 */
@WebServlet(Branches.UI.Extraction.TURN_CRUDE_EDIT)
public class ExtractionTurnCrudeEdit extends IModal {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.EXTRACTION_TURN_CRUDE);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/extraction/crudeTurnEdit.jsp");
        req.setAttribute("laborants", TransportUtil.getLaboratoryPersonal());
        req.setAttribute("saveUrl", Branches.API.EXTRACTION_TURN_CRUDE_EDIT);
        show(req, resp);
    }
}
