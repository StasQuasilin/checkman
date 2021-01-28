package controllers.laboratory.laboratory.vro;

import constants.Branches;
import constants.Titles;
import controllers.IModal;
import utils.turns.TurnBox;

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

    final

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Titles.OIL_MASS_FRACTION);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/vro/oilMassFraction.jsp");
        req.setAttribute("save", Branches.API.OIL_MASS_FRACTION);
        req.setAttribute("turns", TurnBox.getTurns());
        req.setAttribute("forpress", dao.getForpressList());
        show(req, resp);
    }
}
