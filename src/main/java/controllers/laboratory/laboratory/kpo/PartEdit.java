package controllers.laboratory.laboratory.kpo;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import entity.laboratory.transportation.ActType;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 22.05.2019.
 */
@WebServlet(Branches.UI.KPO.PART_EDIT)
public class PartEdit extends IModal {

    private final Logger log = Logger.getLogger(PartEdit.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject json = PostUtil.parseBodyJson(req);
        long id = -1;
        if(json != null) {
            if (json.containsKey(Constants.ID)){
                id = Long.parseLong(String.valueOf(json.get(Constants.ID)));
            }
        }
        if (id != -1) {
            req.setAttribute("part", dao.getKPOPartById(id));
            req.setAttribute("delete", Branches.API.KPO_PART_DELETE);
        }else {
            req.setAttribute("number", dao.getActNumber(ActType.VRO).getNumber() + 1);
        }

        req.setAttribute("title", Titles.PART_EDIT);
        req.setAttribute("turns", TurnBox.getTurns());
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/kpo/partEdit.jsp");
        req.setAttribute("save", Branches.API.KPO_PART_EDIT);
        show(req, resp);
    }
}
