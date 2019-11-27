package controllers.logistic;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;
import entity.DealType;
import entity.transport.TransportCustomer;

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

    final Subscriber[] subscribers = new Subscriber[]{Subscriber.LOAD_PLAN};
    public static final String _CONTENT = "/pages/logistic/logisticList.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, Titles.LOGISTIC_LIST);
        req.setAttribute("findVehicle", Branches.API.References.FIND_VEHICLE);
        req.setAttribute("findTrailer", Branches.API.References.FIND_TRAILER);
        req.setAttribute("findDriver", Branches.API.References.FIND_DRIVER);
        req.setAttribute("findOrganisation", Branches.API.References.FIND_ORGANISATION);
        req.setAttribute("parseVehicle", Branches.API.PARSE_VEHICLE);
        req.setAttribute("parseTrailer", Branches.API.PARSE_TRAILER);
        req.setAttribute("parseDriver", Branches.API.PARSE_PERSON);
        req.setAttribute("parseOrganisation", Branches.API.References.PARSE_ORGANISATION);
        req.setAttribute("save", Branches.API.SAVE_TRANSPORTATION_VEHICLE);
        req.setAttribute("editVehicle", Branches.UI.EDIT_VEHICLE);
        req.setAttribute("editDriver", Branches.UI.EDIT_DRIVER);
        req.setAttribute("editOrganisation", Branches.UI.References.ORGANISATION_EDIT);
        req.setAttribute("saveNote", Branches.API.SAVE_NOTE);
        req.setAttribute("removeNote", Branches.API.REMOVE_NOTE);
        req.setAttribute("dealTypes", DealType.values());
        req.setAttribute(ADD, Branches.UI.WEIGHT_ADD);
        req.setAttribute("cancel", Branches.UI.WEIGHT_CANCEL);
        req.setAttribute("archive", Branches.API.ARCHIVE_LOAD_PLAN);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(FILTER, "/pages/filters/transportFilter.jsp");
        req.setAttribute(SUBSCRIBE, subscribers);
        req.setAttribute(CUSTOMERS, TransportCustomer.values());
        show(req, resp);
    }
}
