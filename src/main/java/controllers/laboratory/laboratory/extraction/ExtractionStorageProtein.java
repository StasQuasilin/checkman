package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.AnalysesType;
import entity.storages.Storage;
import entity.storages.StorageProduct;
import utils.TransportUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 15.05.2019.
 */
@WebServlet(Branches.UI.Extraction.STORAGE_PROTEIN)
public class ExtractionStorageProtein extends IModal {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.EXTRACTION_STORAGE_PROTEIN);
        final List<Storage> storages = new LinkedList<>();
        storages.addAll(hibernator.query(StorageProduct.class, "product/analysesType", AnalysesType.meal)
                .stream().map(StorageProduct::getStorage).collect(Collectors.toList()));
        req.setAttribute("storages", storages);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/extraction/storageProteinEdit.jsp");
        req.setAttribute("laborants", TransportUtil.getLaboratoryPersonal());
        req.setAttribute("save", Branches.API.EXTRACTION_STORAGE_PROTEIN_EDIT);
        show(req, resp);
    }
}
