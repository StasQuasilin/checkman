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
 * Created by szpt_user045 on 11.04.2019.
 */
@WebServlet(Branches.UI.VRO.OIL_EDIT)
public class VROOilEdit extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Titles.OIL_EDIT);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/vro/oilEdit.jsp");
        req.setAttribute("save", Branches.API.VRO_OIL_EDIT);
        req.setAttribute("turns", TurnBox.getTurns());
        show(req, resp);
    }
}
