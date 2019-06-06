package controllers.laboratory.laboratory.storages;

import constants.Branches;
import controllers.IModal;
import entity.AnalysesType;
import entity.storages.StorageProduct;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 06.06.2019.
 */
@WebServlet(Branches.UI.LABORATORY_STORAGE_EDIT)
public class LaboratoryStorageEdit extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AnalysesType type = AnalysesType.valueOf(req.getParameter("type"));
        req.setAttribute("type", type.toString());
        req.setAttribute("storages", hibernator.query(StorageProduct.class, "product/analysesType", type));
        req.setAttribute("title", "title.laboratory.storage.edit");
        req.setAttribute("edit", Branches.API.LABORATORY_STORAGE_EDIT);
        req.setAttribute("modalContent", "/pages/laboratory/storages/storageEdit.jsp");
        show(req, resp);
    }
}
