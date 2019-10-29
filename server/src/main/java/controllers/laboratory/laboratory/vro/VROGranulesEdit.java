package controllers.laboratory.laboratory.vro;

import constants.Branches;
import controllers.IModal;
import entity.laboratory.subdivisions.vro.GranulesAnalyses;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 29.10.2019.
 */
@WebServlet(Branches.UI.VRO.GRANULES)
public class VROGranulesEdit extends IModal {
    private static final String _TITLE = "vro.granules.edit";
    private static final String _CONTENT = "/pages/laboratory/subdivisions/vro/granulesEdit.jsp";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject body = PostUtil.parseBodyJson(req);
        if (body != null) {
            if(body.containsKey(ID)){
                req.setAttribute("granules", dao.getObjectById(GranulesAnalyses.class, body.get(ID)));
            }
        }

        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(TURNS, TurnBox.getTurns());
        req.setAttribute(SAVE, Branches.API.VRO_GRANULAS_EDIT);
        show(req, resp);
    }
}
