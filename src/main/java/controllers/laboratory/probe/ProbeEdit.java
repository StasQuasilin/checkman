package controllers.laboratory.probe;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.AnalysesType;
import entity.laboratory.probes.OilProbe;
import entity.laboratory.probes.SunProbe;
import utils.TransportUtil;
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
                req.setAttribute("title", Constants.Titles.PROBE_SUN_EDIT);
                req.setAttribute("saveApi", Branches.API.PROBE_SUN_SAVE);
                req.setAttribute("modalContent", "/pages/laboratory/probes/sunProbe.jsp");
                if (id != -1){
                    req.setAttribute("probe", hibernator.get(SunProbe.class, "id", id));
                }
                break;
            case oil:
                req.setAttribute("title", Constants.Titles.PROBE_OIL_EDIT);
                req.setAttribute("saveApi", Branches.API.PROBE_OIL_SAVE);
                req.setAttribute("modalContent", "/pages/laboratory/probes/oilProbe.jsp");
                if (id != -1){
                    req.setAttribute("probe", hibernator.get(OilProbe.class, "id", id));
                }
                break;
            case cake:
                req.setAttribute("title", Constants.Titles.PROBE_CAKE_EDIT);
                req.setAttribute("saveApi", Branches.API.PROBE_CAKE_SAVE);
                if (id != -1){
//                    req.setAttribute("probe", hibernator.get(CakeProbe.class, "id", id));
                }
                break;

        }
        req.setAttribute("laborants", TransportUtil.getLaboratoryPersonal());
        req.setAttribute("findManager", Branches.API.References.FIND_WORKER);
        req.setAttribute("findOrganisation", Branches.API.References.FIND_ORGANISATION);
        show(req, resp);


    }
}
