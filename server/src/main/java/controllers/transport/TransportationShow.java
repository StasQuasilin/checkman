package controllers.transport;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import entity.transport.Transportation;
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
        Transportation transportation = dao.getTransportationById(id);
        Weight weight = transportation.getWeight();
        float b = 0;
        float t = 0;
        float n = 0;

        if (weight != null) {
            b = weight.getBrutto();
            t = weight.getTara();
            if (b > 0 && t > 0){
                n = b - t;
            }
        }

        req.setAttribute("brutto", b);
        req.setAttribute("tara", t);
        req.setAttribute("netto", n);
        req.setAttribute("transportation", transportation);
        req.setAttribute("title", Titles.TRANSPORT_SHOW);
        req.setAttribute("timeInLink", Branches.API.TRANSPORT_TIME_IN);
        req.setAttribute("timeOutLink", Branches.API.TRANSPORT_TIME_OUT);
        req.setAttribute("registration", Branches.API.TRANSPORT_REGISTRATION);
        req.setAttribute("findSeals", Branches.API.SEALS_FIND);
        req.setAttribute("saveSeal", Branches.API.SEAL_PUT);
        req.setAttribute("removeSeal", Branches.API.SEAL_REMOVE);
        req.setAttribute("modalContent", "/pages/transport/transportShow.jsp");
        show(req, resp);
    }
}
