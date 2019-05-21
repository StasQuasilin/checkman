package controllers.laboratory.laboratory.vro;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.production.Forpress;
import utils.TransportUtil;
import utils.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 20.05.2019.
 */
@WebServlet(Branches.UI.VRO.OIL_MASS_FRACTION)
public class VROOilMassFractionEdit extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.OIL_MASS_FRACTION);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/vro/oilMassFraction.jsp");
        req.setAttribute("save", Branches.API.OIL_MASS_FRACTION);
        req.setAttribute("turns", TurnBox.getBox().getTurns());
        req.setAttribute("forpress", hibernator.query(Forpress.class, null));
        req.setAttribute("laborants", TransportUtil.getLaboratoryPersonal());
        show(req, resp);
    }
}
