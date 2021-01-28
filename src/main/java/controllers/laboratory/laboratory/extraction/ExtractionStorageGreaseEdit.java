package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import entity.AnalysesType;
import entity.laboratory.subdivisions.extraction.StorageGrease;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.U;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 16.05.2019.
 */
@WebServlet(Branches.UI.Extraction.STORAGE_GREASE)
public class ExtractionStorageGreaseEdit extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StorageGrease grease = null;

        String id = req.getParameter("id");
        if (U.exist(id)){
            grease = dao.getStorageGreaseById(Integer.parseInt(id));
        } else {
            JSONObject body = parseBody(req);
            if (body != null){
                if (body.containsKey(ID)){
                    grease = dao.getStorageGreaseById(body.get(ID));
                }
            }
        }

        if (grease != null) {
            req.setAttribute(GREASE, grease);
        }
        req.setAttribute(TITLE, Titles.EXTRACTION_STORAGE_GREASE);
        req.setAttribute(STORAGES, dao.getStoragesByAnalysesType(AnalysesType.meal));
        req.setAttribute(MODAL_CONTENT, "/pages/laboratory/subdivisions/extraction/storageGreaseEdit.jsp");
        req.setAttribute(TURNS, TurnBox.getTurns());
        req.setAttribute(SAVE, Branches.API.EXTRACTION_STORAGE_GREASE_EDIT);
        show(req, resp);
    }
}
