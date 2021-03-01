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

    private static final String _FILTER = "/pages/laboratory/probes/probeFilter.jsp";
    final Subscriber[] subscribe = new Subscriber[]{Subscriber.PROBES};
    final AnalysesType[] types = new AnalysesType[]{AnalysesType.sun, AnalysesType.oil};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, Titles.PROBE_LIST);
        req.setAttribute(CONTENT, "/pages/laboratory/probes/probeList.jsp");
        req.setAttribute(SHOW, Branches.UI.PROBE_SHOW);
        req.setAttribute(FILTER, _FILTER);
        req.setAttribute(FIND, Branches.API.PROBE_FIND);

        Role role = getRole(req);
        if (role == Role.analyser || role == Role.admin){
            req.setAttribute(TYPES, types);
            req.setAttribute(EDIT, Branches.UI.PROBE_EDIT);
        }
        req.setAttribute(SUBSCRIBE, subscribe);
        show(req, resp);
    }
}
