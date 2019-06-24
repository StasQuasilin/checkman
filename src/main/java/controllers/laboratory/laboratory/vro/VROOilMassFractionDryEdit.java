package controllers.laboratory.laboratory.vro;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.laboratory.subdivisions.vro.OilMassFractionDry;
import entity.production.Forpress;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.TransportUtil;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 20.05.2019.
 */
@WebServlet(Branches.UI.VRO.OIL_MASS_FRACTION_DRY)
public class VROOilMassFractionDryEdit extends IModal {

    private final Logger log = Logger.getLogger(VROOilMassFractionDryEdit.class);
    dbDAO dao = dbDAOService.getDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject json = PostUtil.parseBodyJson(req);
        if (json != null) {
            log.info(json);
            long id = -1;
            if (json.containsKey(Constants.ID)){
                id = Long.parseLong(String.valueOf(json.get(Constants.ID)));
            }
            if (id != -1){
                log.info("Edit " + id);
                req.setAttribute("oil", dao.getOilMassFractionDry(id));
                req.setAttribute("delete", Branches.API.DELETE_OIL_MASS_FRACTION_DRY);
            } else {
                log.info("New document");
            }
        }
        req.setAttribute("title", Constants.Titles.OIL_MASS_FRACTION_DRY);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/vro/oilMassFractionDry.jsp");
        req.setAttribute("save", Branches.API.OIL_MASS_FRACTION_DRY);
        req.setAttribute("turns", TurnBox.getBox().getTurns());
        req.setAttribute("forpress", dao.getForpressList());
        show(req, resp);
    }
}
