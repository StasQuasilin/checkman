package controllers.laboratory.subdivisions;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;
import entity.SubdivisionKey;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 25.03.2019.
 */
@WebServlet(Branches.UI.SUBDIVISION_LIST)
public class SubdivisionList extends IUIServlet {

    final Subscriber[] extractionSubscribes = new Subscriber[]{Subscriber.EXTRACTION};
    final Subscriber[] vroSubscribes = new Subscriber[]{Subscriber.VRO};
    final Subscriber[] kpoSubscribes = new Subscriber[]{Subscriber.KPO};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SubdivisionKey key = SubdivisionKey.valueOf(req.getParameter("sub"));

        switch (key){
            case ex:
                req.setAttribute(TITLE, Titles.SUBDIVISION_LIST_EXTRACTION);
                req.setAttribute(CONTENT, "/pages/laboratory/subdivisions/extractionList.jsp");
                req.setAttribute("crudeEdit", Branches.UI.Extraction.CRUDE_EDIT);
                req.setAttribute("turnProtein", Branches.UI.Extraction.TURN_PROTEIN);
                req.setAttribute("storageProtein", Branches.UI.Extraction.STORAGE_PROTEIN);
                req.setAttribute("turnGrease", Branches.UI.Extraction.TURN_GREASE);
                req.setAttribute("storageGrease", Branches.UI.Extraction.STORAGE_GREASE);
                req.setAttribute("oilEdit", Branches.UI.Extraction.OIL_EDIT);
                req.setAttribute("dailyPrint", Branches.UI.Extraction.DAILY_REPORT_PRINT);
                req.setAttribute("update", Branches.API.EXTRACTION_LIST);
                req.setAttribute(SUBSCRIBE, extractionSubscribes);
                break;
            case vro:
                req.setAttribute(TITLE, Titles.SUBDIVISION_LIST_VRO);
                req.setAttribute(CONTENT, "/pages/laboratory/subdivisions/vro/vroList.jsp");
                req.setAttribute("crudeEdit", Branches.UI.VRO.CRUDE_EDIT);
                req.setAttribute("oilEdit", Branches.UI.VRO.OIL_EDIT);
                req.setAttribute("dailyEdit", Branches.UI.VRO.DAILY_EDIT);
                req.setAttribute("oilMassFraction", Branches.UI.VRO.OIL_MASS_FRACTION);
                req.setAttribute("oilMassFractionDry", Branches.UI.VRO.OIL_MASS_FRACTION_DRY);
                req.setAttribute("granules", Branches.UI.VRO.GRANULES);
                req.setAttribute("update", Branches.API.VRO_LIST);
                req.setAttribute("dailyPrint", Branches.UI.VRO.DAILY_REPORT_PRINT);
                req.setAttribute("forpress", dao.getForpressList());
                req.setAttribute(SUBSCRIBE, vroSubscribes);
                break;
            case kpo:
                req.setAttribute(TITLE, Titles.SUBDIVISION_LIST_KPO);
                req.setAttribute(CONTENT, "/pages/laboratory/subdivisions/kpoList.jsp");
                req.setAttribute(EDIT, Branches.UI.KPO.PART_EDIT);
                req.setAttribute(SUBSCRIBE, kpoSubscribes);
                break;
        }
//        req.setAttribute("filter", "/pages/laboratory/subdivisions/subdivisionFilter.jsp");
        show(req, resp);
    }
}
