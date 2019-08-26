package controllers.laboratory.probe;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import entity.AnalysesType;
import utils.U;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 01.04.2019.
 */
@WebServlet(Branches.UI.PROBE_EDIT)
public class ProbeEdit extends IModal {

    final static String TITLE = Constants.TITLE;
    final static String SAVE = Constants.SAVE;
    final static String MODAL_CONTENT = Constants.MODAL_CONTENT;
    final static String PROBE = Constants.PROBE;
    final static String FIND_MANAGER = Constants.FIND_MANGER;
    final static String FIND_ORGANISATION = Constants.FIND_ORGANISATION;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AnalysesType type = AnalysesType.valueOf(req.getParameter(Constants.TYPE));
        String idParameter = req.getParameter("id");
        int id = -1;
        if (U.exist(idParameter)){
            id = Integer.parseInt(idParameter);
        }
        switch (type){
            case sun:
                req.setAttribute(TITLE, Titles.PROBE_SUN_EDIT);
                req.setAttribute(SAVE, Branches.API.PROBE_SUN_SAVE);
                req.setAttribute(MODAL_CONTENT, "/pages/laboratory/probes/sunProbe.jsp");
                if (id != -1){
                    req.setAttribute(PROBE, dao.getSunProbeById(id));
                }
                break;
            case oil:
                req.setAttribute(TITLE, Titles.PROBE_OIL_EDIT);
                req.setAttribute(SAVE, Branches.API.PROBE_OIL_SAVE);
                req.setAttribute(MODAL_CONTENT, "/pages/laboratory/probes/oilProbe.jsp");
                if (id != -1){
                    req.setAttribute(PROBE, dao.getOilProbeById(id));
                }
                break;
            case cake:
                req.setAttribute(TITLE, Titles.PROBE_CAKE_EDIT);
                req.setAttribute(SAVE, Branches.API.PROBE_CAKE_SAVE);
//                req.setAttribute(MODAL_CONTENT, "/pages/laboratory/probes/cakeProbe.jsp");
                if (id != -1){
//                    req.setAttribute("probe", dao.getCake);
                }
                break;

        }
        req.setAttribute(FIND_MANAGER, Branches.API.References.FIND_WORKER);
        req.setAttribute(FIND_ORGANISATION, Branches.API.References.FIND_ORGANISATION);
        show(req, resp);


    }
}
