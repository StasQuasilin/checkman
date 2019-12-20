package controllers.laboratory.laboratory.reports;

import constants.Branches;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 25.11.2019.
 */
@WebServlet(Branches.UI.LABORATORY_CARRIAGES)
public class LaboratoryCarriages extends IModal {

    private static final String _TITLE = "title.reports.transport.carriage";
    private static final String _CONTENT = "/pages/laboratory/reports/laboratoryCarriage.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(FIND_ORGANISATION, Branches.API.References.FIND_ORGANISATION);
        req.setAttribute(FIND_DRIVER, Branches.API.References.FIND_DRIVER);
        req.setAttribute(PRINT, Branches.API.LABORATORY_CARRIAGE);
        req.setAttribute(PRODUCTS, dao.getProductList());
        show(req, resp);
    }
}
