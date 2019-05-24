package controllers.laboratory.laboratory.vro;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.laboratory.subdivisions.vro.VRODaily;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.TransportUtil;
import utils.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 11.04.2019.
 */
@WebServlet(Branches.UI.VRO.DAILY_EDIT)
public class VRODailyEdit extends IModal{

    private final Logger log = Logger.getLogger(VRODailyEdit.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject json = PostUtil.parseBodyJson(req);
        if (json != null) {
            log.info(json);
            long id = -1;
            if (json.containsKey(Constants.ID)){
                id = Long.parseLong(String.valueOf(json.get(Constants.ID)));
            }
            if (id != -1) {
                req.setAttribute("daily", hibernator.get(VRODaily.class, "id", id));
            }
        }
        req.setAttribute("title", Constants.Titles.DAILY_ANALYSES);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/vro/vroDaily.jsp");
        req.setAttribute("save", Branches.API.VRO_DAILY_EDIT);
        req.setAttribute("turns", TurnBox.getBox().getTurns());
        req.setAttribute("laborants", TransportUtil.getLaboratoryPersonal());
        show(req, resp);
    }
}
