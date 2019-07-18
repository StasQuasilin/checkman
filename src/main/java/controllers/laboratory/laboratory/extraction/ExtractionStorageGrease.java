package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.AnalysesType;
import entity.laboratory.subdivisions.extraction.StorageGrease;
import entity.storages.Storage;
import entity.storages.StorageProduct;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.TransportUtil;
import utils.U;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 16.05.2019.
 */
@WebServlet(Branches.UI.Extraction.STORAGE_GREASE)
public class ExtractionStorageGrease extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StorageGrease grease = null;

        String id = req.getParameter("id");
        if (U.exist(id)){
            grease = dao.getStorageGreaseById(Integer.parseInt(id));
        } else {
            JSONObject body = PostUtil.parseBodyJson(req);
            if (body != null){
                if (body.containsKey(Constants.ID)){
                    grease = dao.getStorageGreaseById(body.get(Constants.ID));
                }
            }
        }

        if (grease != null) {
            req.setAttribute("grease", grease);
        }
        req.setAttribute("title", Constants.Titles.EXTRACTION_STORAGE_GREASE);
        req.setAttribute("storages", dao.getStoragesByAnalysesType(AnalysesType.meal));
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/extraction/storageGreaseEdit.jsp");
        req.setAttribute("turns", TurnBox.getBox().getTurns());
        req.setAttribute("save", Branches.API.EXTRACTION_STORAGE_GREASE_EDIT);
        show(req, resp);
    }
}
