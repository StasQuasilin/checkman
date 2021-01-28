package controllers.laboratory.laboratory.vro;

import constants.Branches;
import controllers.IModal;
import entity.laboratory.subdivisions.vro.SunProtein;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 30.10.2019.
 */
@WebServlet(Branches.UI.VRO.SUN_PROTEIN)
public class VROSunProtein extends IModal {
    private static final String _TITLE = "title.vro.sun.protein";
    private static final String _CONTENT = "/pages/laboratory/subdivisions/vro/sunProteinEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject json = PostUtil.parseBodyJson(req);
        if (json != null) {
            if (json.containsKey(ID)){
                req.setAttribute("protein", dao.getObjectById(SunProtein.class, json.get(ID)));
            }
        }

        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(TURNS, TurnBox.getTurns());
        req.setAttribute(SAVE, Branches.API.VRO_SUN_PROTEIN);
        show(req, resp);
    }
}
