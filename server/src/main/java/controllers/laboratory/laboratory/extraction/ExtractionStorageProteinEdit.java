package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import entity.AnalysesType;
import entity.laboratory.subdivisions.extraction.StorageProtein;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.U;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 15.05.2019.
 */
@WebServlet(Branches.UI.Extraction.STORAGE_PROTEIN)
public class ExtractionStorageProteinEdit extends IModal {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StorageProtein protein = null;
        String id = req.getParameter("id");
        if (U.exist(id)){
            protein = dao.getStorageProteinById(Integer.parseInt(id));
        } else {
            JSONObject body = PostUtil.parseBodyJson(req);
            if (body != null){
                if (body.containsKey(Constants.ID)){
                    protein = dao.getStorageProteinById(body.get(Constants.ID));
                }
            }
        }

        if (protein != null) {
            req.setAttribute("protein", protein);
        }

        req.setAttribute("title", Titles.EXTRACTION_STORAGE_PROTEIN);
        req.setAttribute("storages", dao.getStoragesByAnalysesType(AnalysesType.meal));
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/extraction/storageProteinEdit.jsp");
        req.setAttribute("save", Branches.API.EXTRACTION_STORAGE_PROTEIN_EDIT);
        show(req, resp);
    }
}
