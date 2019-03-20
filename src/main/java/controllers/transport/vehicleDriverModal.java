package controllers.transport;

import constants.Branches;
import constants.Constants;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 20.03.2019.
 */
@WebServlet(Branches.UI.VEHICLE_DRIVER_MODAL)
public class vehicleDriverModal extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.VEHICLE_DRIVER_INPUT);
        req.setAttribute("modalContent", "/pages/transport/vehicleDriverInput.jsp");
        show(req, resp);
    }
}
