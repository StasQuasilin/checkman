package controllers.transport;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.documents.LoadPlan;

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
        try {
            int id = Integer.parseInt(req.getParameter(Constants.ID));
            req.setAttribute("plan", hibernator.get(LoadPlan.class, "id", id));
            req.setAttribute("title", Constants.Titles.TRANSPORT_SHOW);
            req.setAttribute("timeLink", Branches.API.TRANSPORT_TIME);
            req.setAttribute("modalContent", "/pages/transport/transportShow.jsp");
            show(req, resp);
        } catch (Exception ignored){

        }
    }
}
