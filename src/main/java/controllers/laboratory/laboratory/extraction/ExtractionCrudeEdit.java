package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import constants.Constants;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 28.03.2019.
 */
@WebServlet(Branches.UI.Extraction.CRUDE_EDIT)
public class ExtractionCrudeEdit extends IModal {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.EXTRACTION_CRUDE);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/extraction/crudeEdit.jsp");
        show(req, resp);
    }
}
