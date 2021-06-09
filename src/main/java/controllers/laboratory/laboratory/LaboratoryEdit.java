package controllers.laboratory.laboratory;

import constants.Branches;
import constants.Titles;
import controllers.IModal;
import entity.AnalysesType;
import entity.products.Product;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;
import utils.hibernate.dao.TransportationDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@WebServlet(Branches.UI.LABORATORY_EDIT)
public class LaboratoryEdit extends IModal {

    private static final String _TITLE = "title.laboratory.edit";
    private final TransportationDAO transportationDAO = new TransportationDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBodyGood(req);
        if (body != null){
            Transportation transportation = transportationDAO.getTransportation(body.get(ID));
            req.setAttribute(TRANSPORTATION, transportation);
            req.setAttribute(PRINT, Branches.UI.LABORATORY_PRINT_OPTIONS);
            req.setAttribute(SAVE, Branches.API.LABORATORY_SAVE_SUN);
            req.setAttribute(MODAL_CONTENT, "/pages/laboratory/laboratoryEdit.jsp");
            req.setAttribute(TITLE, _TITLE);
            show(req, resp);
        }

    }
}
