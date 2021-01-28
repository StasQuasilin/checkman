package controllers.laboratory.laboratory.storages;

import api.sockets.Subscriber;
import constants.Branches;
import controllers.IUIServlet;
import entity.AnalysesType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 06.06.2019.
 */
@WebServlet(Branches.UI.LABORATORY_STORAGES)
public class LaboratoryStoragesList extends IUIServlet {

    final Subscriber[] subscribers = new Subscriber[]{Subscriber.LABORATORY_STORAGES};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AnalysesType analysesType = AnalysesType.valueOf(req.getParameter("type"));
        req.setAttribute("type", analysesType.toString());
        req.setAttribute("title", "title.laboratory.storages");
        req.setAttribute("content", "/pages/laboratory/storages/storageList.jsp");
        req.setAttribute("edit", Branches.UI.LABORATORY_STORAGE_EDIT);
        req.setAttribute("subscribe", subscribers);

        show(req, resp);
    }
}
