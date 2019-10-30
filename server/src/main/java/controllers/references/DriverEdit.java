package controllers.references;

import constants.Branches;
import controllers.IModal;
import entity.transport.Driver;
import utils.U;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 18.06.2019.
 */
@WebServlet(Branches.UI.References.DRIVER_EDIT)
public class DriverEdit extends IModal {

    private static final String ORGANISATION_EDIT = "organisationEdit";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter(ID);
        if (U.exist(id)){
            req.setAttribute("driver", dao.getDriverByID(Integer.parseInt(id)));
        }
        req.setAttribute(TITLE, "driver.edit");
        req.setAttribute(MODAL_CONTENT, "/pages/references/driverEdit.jsp");
        req.setAttribute(ORGANISATION_EDIT, Branches.UI.References.ORGANISATION_EDIT);
        req.setAttribute(SAVE, Branches.API.References.DRIVER_EDIT);
        show(req, resp);
    }
}
