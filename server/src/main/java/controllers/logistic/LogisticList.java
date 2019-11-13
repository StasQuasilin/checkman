package controllers.logistic;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Titles;
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

    private static final long serialVersionUID = 8487428665694665680L;
    final Subscriber[] subscribers = new Subscriber[]{Subscriber.TRANSPORT};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, Titles.LOGISTIC_LIST);
        req.setAttribute("findVehicle", Branches.API.References.FIND_VEHICLE);
        req.setAttribute("findDriver", Branches.API.References.FIND_DRIVER);
        req.setAttribute("parseVehicle", Branches.API.PARSE_VEHICLE);
        req.setAttribute("parseDriver", Branches.API.PARSE_PERSON);
        req.setAttribute("saveVehicle", Branches.API.SAVE_TRANSPORTATION_VEHICLE);
        req.setAttribute("saveDriver", Branches.API.SAVE_TRANSPORTATION_DRIVER);
        req.setAttribute("editVehicle", Branches.UI.EDIT_VEHICLE);
        req.setAttribute("editDriver", Branches.UI.EDIT_DRIVER);
        req.setAttribute("saveNote", Branches.API.SAVE_NOTE);
        req.setAttribute("removeNote", Branches.API.REMOVE_NOTE);
        req.setAttribute("dealTypes", DealType.values());
        req.setAttribute("changeDate", Branches.API.CHANGE_DATE);
        req.setAttribute(ADD, Branches.UI.WEIGHT_ADD);
        req.setAttribute("cancel", Branches.UI.WEIGHT_CANCEL);
        req.setAttribute(CONTENT, "/pages/logistic/logisticList.jsp");
        req.setAttribute(FILTER, "/pages/filters/transportFilter.jsp");
        req.setAttribute(SUBSCRIBE, subscribers);
        show(req, resp);
    }
}
