package controllers.laboratory.laboratory.kpo;

import constants.Branches;
import constants.Constants;
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
 * Created by szpt_user045 on 22.05.2019.
 */
@WebServlet(Branches.UI.KPO.PART_EDIT)
public class PartEdit extends IModal {

    private final Logger log = Logger.getLogger(PartEdit.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject json = PostUtil.parseBodyJson(req);
        if(json != null) {
            long id = -1;
            if (json.containsKey(Constants.ID)){
                id = Long.parseLong(String.valueOf(json.get(Constants.ID)));
            }
            if (id != -1) {
                req.setAttribute("part", dao.getKPOPartById(id));
                req.setAttribute("delete", Branches.API.KPO_PART_DELETE);
            }
        }

        req.setAttribute("title", Constants.Titles.PART_EDIT);
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/kpo/partEdit.jsp");
        req.setAttribute("save", Branches.API.KPO_PART_EDIT);
        show(req, resp);
    }
}
