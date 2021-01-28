package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by szpt_user045 on 28.03.2019.
 */
@WebServlet(Branches.UI.Extraction.CRUDE_EDIT)
public class ExtractionCrudeEdit extends IModal {

    private final Logger log = Logger.getLogger(ExtractionCrudeEdit.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject body = parseBody(req);

        if (body != null) {
            log.info(body);
            long id = -1;
            if (body.containsKey(ID)){
                id = Long.parseLong(String.valueOf(body.remove(ID)));
            }
            if (id != -1) {
                req.setAttribute("crude", dao.getExtractionCrudeById(id));
            }
            req.setAttribute(DATE, body.get(DATE));
        }

        req.setAttribute("title", Titles.EXTRACTION_CRUDE);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/extraction/crudeEdit.jsp");
        req.setAttribute("save", Branches.API.EXTRACTION_CRUDE_EDIT);
        req.setAttribute("remove", Branches.UI.EXTRACTION_CRUDE_REMOVE);
        show(req, resp);
    }
}
