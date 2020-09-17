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

    final Subscriber[] subscribers = new Subscriber[]{Subscriber.TRANSPORT_BUY, Subscriber.TRANSPORT_SELL};
    public static final String _CONTENT = "/pages/logistic/logisticList.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, Titles.LOGISTIC_LIST);
        req.setAttribute(FIND_VEHICLE, Branches.API.References.FIND_VEHICLE);
        req.setAttribute(FIND_TRAILER, Branches.API.References.FIND_TRAILER);
        req.setAttribute(FIND_DRIVER, Branches.API.References.FIND_DRIVER);
        req.setAttribute(FIND_ORGANISATION, Branches.API.References.FIND_ORGANISATION);
        req.setAttribute(PARSE_VEHICLE, Branches.API.PARSE_VEHICLE);
        req.setAttribute(PARSE_TRAILER, Branches.API.PARSE_TRAILER);
        req.setAttribute(PARSE_DRIVER, Branches.API.PARSE_PERSON);
        req.setAttribute(PARSE_ORGANISATION, Branches.API.References.PARSE_ORGANISATION);
        req.setAttribute(SAVE, Branches.API.SAVE_TRANSPORTATION_VEHICLE);
        req.setAttribute(EDIT_VEHICLE, Branches.UI.EDIT_VEHICLE);
        req.setAttribute(EDIT_DRIVER, Branches.UI.EDIT_DRIVER);
        req.setAttribute(EDIT_ORGANISATION, Branches.UI.References.ORGANISATION_EDIT);
        req.setAttribute("saveNote", Branches.API.SAVE_NOTE);
        req.setAttribute("removeNote", Branches.API.REMOVE_NOTE);
        req.setAttribute("dealTypes", DealType.values());
        req.setAttribute(ADD, Branches.UI.WEIGHT_ADD);
        req.setAttribute(CANCEL, Branches.UI.WEIGHT_CANCEL);
        req.setAttribute(ARCHIVE, Branches.API.ARCHIVE_LOAD_PLAN);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(FILTER, "/pages/filters/transportFilter.jsp");
        req.setAttribute(SUBSCRIBE, subscribers);
        req.setAttribute(CUSTOMERS, TransportCustomer.values());
        req.setAttribute(TRANSPORT_CARRIAGES, Branches.UI.TRANSPORT_CARRIAGES);
        req.setAttribute(TRANSPORT_COST, Branches.UI.TRANSPORT_COST);
        req.setAttribute(PRINT, Branches.UI.SUMMARY_PLAN_PRINT);
        show(req, resp);
    }
}
