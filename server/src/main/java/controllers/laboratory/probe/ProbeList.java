package controllers.laboratory.probe;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;
import entity.AnalysesType;
import entity.Role;
import entity.Worker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 25.03.2019.
 */
@WebServlet(Branches.UI.PROBE_LIST)
public class ProbeList extends IUIServlet {

    final Subscriber[] subscribe = new Subscriber[]{Subscriber.PROBES};
    final AnalysesType[] types = new AnalysesType[]{AnalysesType.sun, AnalysesType.oil, AnalysesType.cake};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, Titles.PROBE_LIST);
        req.setAttribute(CONTENT, "/pages/laboratory/probeList.jsp");
        req.setAttribute("show", Branches.UI.PROBE_SHOW);

        Role role = getRole(req);
        if (role == Role.analyser || role == Role.admin){
            req.setAttribute("analysesTypes", types);
            req.setAttribute(EDIT, Branches.UI.PROBE_EDIT);
        }
        req.setAttribute(SUBSCRIBE, subscribe);
        show(req, resp);
    }
}
