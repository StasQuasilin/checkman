package controllers.laboratory.laboratory.vro;

import constants.Branches;
import constants.Titles;
import controllers.IModal;
import entity.laboratory.subdivisions.vro.VROOil;
import org.json.simple.JSONObject;
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

    private static final String _CONTENT = "/pages/laboratory/subdivisions/vro/oilEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            req.setAttribute(OIL, dao.getObjectById(VROOil.class, body.get(ID)));
        }
        req.setAttribute(TITLE, Titles.OIL_EDIT);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(SAVE, Branches.API.VRO_OIL_EDIT);
        req.setAttribute(TURNS, TurnBox.getTurns());
        show(req, resp);
    }
}
