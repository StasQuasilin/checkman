package controllers.laboratory.laboratory.vro;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 10.04.2019.
 */
@WebServlet(Branches.UI.VRO.CRUDE_EDIT)
public class VROCrudeEdit extends IModal {

    private final Logger log = Logger.getLogger(VROCrudeEdit.class);


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject json = PostUtil.parseBodyJson(req);
        if (json != null) {
            log.info(json);
            Object id = null;
            if (json.containsKey(Constants.ID)){
                id = json.get(Constants.ID);
            }

            if (id != null) {
                log.info("Edit crude: " + id);
                req.setAttribute("crude", dao.getVroCrudeById(id));
            } else {
                log.info("Create new crude");
            }
        }
        req.setAttribute("title", Titles.SUN_EDIT);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/vro/crudeEdit.jsp");
        req.setAttribute("save", Branches.API.VRO_CRUDE_EDIT);
        req.setAttribute("forpress", dao.getForpressList());
        show(req, resp);
    }
}
