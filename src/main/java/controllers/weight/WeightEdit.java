package controllers.weight;

import constants.Branches;
import constants.Titles;
import controllers.IModal;
import entity.transport.Transportation;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 19.03.2019.
 */
@WebServlet(Branches.UI.WEIGHT_EDIT)
public class WeightEdit extends IModal {

    private static final String _CONTENT = "/pages/weight/weightEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if(body != null){
            final Object id = body.get(ID);
            if (id != null) {
                Transportation plan = dao.getObjectById(Transportation.class, id);
                req.setAttribute(PLAN, plan);
                req.setAttribute(SEALS, dao.getSealsByTransportation(plan));
            }
            req.setAttribute(SAVE, Branches.API.SAVE_WEIGHT);
            req.setAttribute(TITLE, Titles.WEIGHT_EDIT);
            req.setAttribute(MODAL_CONTENT, _CONTENT);
            req.setAttribute(PRINT, Branches.UI.WAYBILL_PRINT);
            show(req, resp);
        }

    }
}
