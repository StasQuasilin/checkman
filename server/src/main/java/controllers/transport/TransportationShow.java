package controllers.transport;

import constants.Branches;
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

    private static final String _CONTENT = "/pages/transport/transportShow.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter(ID));
        Transportation transportation = dao.getObjectById(Transportation.class, id);
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

        req.setAttribute(BRUTTO, b);
        req.setAttribute(TARA, t);
        req.setAttribute(NETTO, n);
        req.setAttribute(TRANSPORTATION, transportation);
        req.setAttribute(TITLE, Titles.TRANSPORT_SHOW);
        req.setAttribute(SAVE_TIME, Branches.API.TRANSPORT_TIME);
        req.setAttribute(REMOVE_TIME, Branches.API.REMOVE_TRANSPORT_TIME);
        req.setAttribute(REGISTRATION, Branches.API.TRANSPORT_REGISTRATION);
        req.setAttribute(FIND_SEALS, Branches.API.SEALS_FIND);
        req.setAttribute(SAVE_SEAL, Branches.API.SEAL_PUT);
        req.setAttribute(REMOVE_SEAL, Branches.API.SEAL_REMOVE);
        req.setAttribute(SEALS, dao.getSealsByTransportation(transportation));
        req.setAttribute(TRUCK_INFO, Branches.UI.TRUCK_INFO);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        show(req, resp);
    }
}
