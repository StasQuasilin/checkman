package controllers.logistic;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Constants;
import controllers.IUIServlet;
import entity.DealType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.UI.LOGISTIC_LIST)
public class LogisticList extends IUIServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.LOGISTIC_LIST);
        req.setAttribute("findVehicleApi", Branches.API.References.FIND_VEHICLE);
        req.setAttribute("findDriverApi", Branches.API.References.FIND_DRIVER);
        req.setAttribute("saveTransportationVehicleAPI", Branches.API.SAVE_TRANSPORTATION_VEHICLE);
        req.setAttribute("saveTransportationDriverAPI", Branches.API.SAVE_TRANSPORTATION_DRIVER);
        req.setAttribute("save", Branches.API.LOGISTIC_SAVE);
        req.setAttribute("saveNote", Branches.API.SAVE_NOTE);
        req.setAttribute("removeNote", Branches.API.REMOVE_NOTE);
        req.setAttribute("dealTypes", DealType.values());
        req.setAttribute("vehicleInput", Branches.UI.VEHICLE_MODAL);
        req.setAttribute("driverInput", Branches.UI.DRIVER_MODAL);
        req.setAttribute("vehicleDriverInput", Branches.UI.VEHICLE_DRIVER_MODAL);
        req.setAttribute("changeDate", Branches.API.CHANGE_DATE);
        req.setAttribute("content", "/pages/logistic/logisticList.jsp");
        req.setAttribute("filter", "/pages/filters/transportFilter.jsp");
        req.setAttribute("subscribe", Subscriber.TRANSPORT);
        show(req, resp);
    }
}
