package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import constants.Titles;
import controllers.IModal;
import entity.laboratory.subdivisions.extraction.ExtractionCrude;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kvasik on 22.08.2019.
 */
@WebServlet(Branches.UI.EXTRACTION_CRUDE_REMOVE)
public class ExtractionCrudeRemove extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id =   Integer.parseInt(req.getParameter("id"));
        ExtractionCrude crude = dao.getExtractionCrudeById(id);
        req.setAttribute("crude", crude);
        req.setAttribute("title", Titles.EXTRACTION_CRUDE_REMOVE);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/extraction/crudeRemove.jsp");
        req.setAttribute("remove", Branches.API.EXTRACTION_CRUDE_REMOVE);
        show(req, resp);
    }
}
