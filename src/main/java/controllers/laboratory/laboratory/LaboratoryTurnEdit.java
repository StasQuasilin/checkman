package controllers.laboratory.laboratory;

import constants.Branches;
import controllers.IModal;
import entity.laboratory.LaboratoryTurn;
import entity.production.Turn;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.TransportUtil;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 03.06.2019.
 */
@WebServlet(Branches.UI.LABORATORY_TURNS_EDIT)
public class LaboratoryTurnEdit extends IModal {

    private final TurnBox turnBox = TurnBox.getBox();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject body = PostUtil.parseBodyJson(req);
        if(body != null) {

        }

        req.setAttribute("title", "title.laboratory.turn.edit");
        req.setAttribute("turns", turnBox.getTurns());
        req.setAttribute("modalContent", "/pages/laboratory/turnEdit.jsp");
        req.setAttribute("save", Branches.API.LABORATORY_TURN_EDIT);
        show(req, resp);
    }
}
