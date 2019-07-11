package controllers.laboratory.probe;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Constants;
import controllers.IUIServlet;
import entity.AnalysesType;

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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.PROBE_LIST);
        req.setAttribute("content", "/pages/laboratory/probeList.jsp");
        req.setAttribute("show", Branches.UI.PROBE_SHOW);
        req.setAttribute("edit", Branches.UI.PROBE_EDIT);
        req.setAttribute("analysesTypes", AnalysesType.values());
        req.setAttribute("filter", "/pages/filters/archiveFilter.jsp");
        req.setAttribute("subscribe", Subscriber.PROBES);
        show(req, resp);
    }
}
