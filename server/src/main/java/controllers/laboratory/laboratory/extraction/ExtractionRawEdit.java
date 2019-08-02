package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.laboratory.subdivisions.extraction.ExtractionRaw;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.TransportUtil;
import utils.U;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 28.03.2019.
 */
@WebServlet(Branches.UI.Extraction.RAW_EDIT)
public class ExtractionRawEdit extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExtractionRaw raw = null;
        String id = req.getParameter("id");
        if (U.exist(id)){
            raw = dao.getExtractionRawById(Integer.parseInt(id));
        } else {
            JSONObject body = PostUtil.parseBodyJson(req);
            if (body != null){
                if (body.containsKey(Constants.ID)){
                    raw = dao.getExtractionRawById(body.get(Constants.ID));
                }
            }
        }

        if (raw != null) {
            req.setAttribute("raw", raw);
        }
        req.setAttribute("title", Constants.Titles.EXTRACTION_RAW);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/extraction/rawEdit.jsp");
        req.setAttribute("saveUrl", Branches.API.EXTRACTION_RAW_EDIT);
        show(req, resp);
    }
}
