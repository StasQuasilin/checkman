package controllers.weight;

import constants.Branches;
import controllers.IModal;
import entity.seals.Seal;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.hibernate.dao.SealDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 25.04.2019.
 */
@WebServlet(Branches.UI.WEIGHT_CANCEL)
public class WeightCancel extends IModal {

    private final SealDAO sealDAO = new SealDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {

            Object id = body.get(ID);

            Transportation transportation = dao.getObjectById(Transportation.class, id);
            if (transportation != null) {
                if (!transportation.isArchive()) {
                    req.setAttribute(MODAL_CONTENT, "/pages/weight/weightCancel.jsp");
                    req.setAttribute(PLAN, transportation);
                    req.setAttribute(CANCEL, Branches.API.REMOVE_PLAN);
                } else {
                    req.setAttribute(MODAL_CONTENT, "/pages/weight/weightCancelImpossible.jsp");
                }

                final List<Seal> seals = sealDAO.getSealsByTransportation(transportation);
                if (transportation.any() || seals.size() > 0){
                    req.setAttribute(SEALS, seals);
                    req.setAttribute(TITLE, "title.weight.archive");
                } else {
                    req.setAttribute(TITLE, "title.weight.cancel");
                }

                show(req, resp);
            }
        }
    }
}
