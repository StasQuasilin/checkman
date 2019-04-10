package controllers.laboratory.laboratory.vro;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.production.Forpress;
import utils.TransportUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 10.04.2019.
 */
@WebServlet(Branches.UI.VRO.CRUDE_EDIT)
public class VROCrudeEdit extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.SUN_EDIT);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/vro/crudeEdit.jsp");
        req.setAttribute("save", Branches.API.VRO_CRUDE_EDIT);
        req.setAttribute("laborants", TransportUtil.getLaboratoryPersonal());
        req.setAttribute("forpress", hibernator.query(Forpress.class, null));
        show(req, resp);
    }
}
