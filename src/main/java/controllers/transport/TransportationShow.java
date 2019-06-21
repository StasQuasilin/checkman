package controllers.transport;

import api.transport.TransportDirection;
import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.documents.LoadPlan;
import entity.weight.Weight;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 19.03.2019.
 */
@WebServlet(Branches.UI.TRANSPORT_SHOW)
public class TransportationShow extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter(Constants.ID));
        LoadPlan loadPlan = hibernator.get(LoadPlan.class, "id", id);
        Weight weight = loadPlan.getTransportation().getWeight();
        float b = 0;
        float t = 0;
        float n = 0;

        if (weight != null) {
            b = weight.getBrutto();
            t = weight.getTara();
            n = weight.getNetto();
        }

        req.setAttribute("brutto", b);
        req.setAttribute("tara", t);
        req.setAttribute("netto", n);
        req.setAttribute("plan", loadPlan);
        req.setAttribute("title", Constants.Titles.TRANSPORT_SHOW);
        req.setAttribute("timeInLink", Branches.API.TRANSPORT_TIME_IN);
        req.setAttribute("timeOutLink", Branches.API.TRANSPORT_TIME_OUT);
        req.setAttribute("findSeals", Branches.API.SEALS_FIND);
        req.setAttribute("saveSeal", Branches.API.SEAL_PUT);
        req.setAttribute("removeSeal", Branches.API.SEAL_REMOVE);
        req.setAttribute("modalContent", "/pages/transport/transportShow.jsp");
        show(req, resp);
    }
}
