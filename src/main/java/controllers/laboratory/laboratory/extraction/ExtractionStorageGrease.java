package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.AnalysesType;
import entity.storages.Storage;
import entity.storages.StorageProduct;
import utils.TransportUtil;
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
        req.setAttribute("title", Constants.Titles.EXTRACTION_STORAGE_GREASE);
        final List<Storage> storages = new LinkedList<>();
        storages.addAll(hibernator.query(StorageProduct.class, "product/analysesType", AnalysesType.meal)
                .stream().map(StorageProduct::getStorage).collect(Collectors.toList()));
        req.setAttribute("storages", storages);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/extraction/storageGreaseEdit.jsp");
        req.setAttribute("turns", TurnBox.getBox().getTurns());
        req.setAttribute("laborants", TransportUtil.getLaboratoryPersonal());
        req.setAttribute("save", Branches.API.EXTRACTION_STORAGE_GREASE_EDIT);
        show(req, resp);
    }
}
