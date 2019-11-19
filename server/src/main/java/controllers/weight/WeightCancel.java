package controllers.weight;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.documents.LoadPlan;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 25.04.2019.
 */
@WebServlet(Branches.UI.WEIGHT_CANCEL)
public class WeightCancel extends IModal {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        if (body != null) {

            Transportation transportation;
            Object id = body.get(ID);
            LoadPlan plan = dao.getObjectById(LoadPlan.class, id);
            if (plan != null) {
                transportation = plan.getTransportation();
            } else {
                transportation = dao.getObjectById(Transportation.class, id);
            }
            if (transportation != null) {
                if (!transportation.isArchive()) {
                    req.setAttribute(MODAL_CONTENT, "/pages/weight/weightCancel.jsp");
                    req.setAttribute("plan", plan);
                    req.setAttribute("cancel", Branches.API.REMOVE_PLAN);
                } else {
                    req.setAttribute(MODAL_CONTENT, "/pages/weight/weightCancelImpossible.jsp");
                }
                req.setAttribute(TITLE, "title.weight.cancel");
                show(req, resp);
            }
        }
    }
}
