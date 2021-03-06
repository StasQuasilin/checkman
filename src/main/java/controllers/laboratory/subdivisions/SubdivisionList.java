package controllers.laboratory.subdivisions;

import api.sockets.Subscribe;
import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;
import entity.Role;
import entity.SubdivisionKey;
import utils.turns.TurnBox;

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

    final Subscribe[] extractionSubscribes = new Subscribe[]{Subscribe.EXTRACTION};
    final Subscribe[] vroSubscribes = new Subscribe[]{Subscribe.VRO};
    final Subscribe[] kpoSubscribes = new Subscribe[]{Subscribe.KPO};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SubdivisionKey key = SubdivisionKey.valueOf(req.getParameter("sub"));
        Role role = getRole(req);

        switch (key){
            case ex:
                req.setAttribute(TITLE, Titles.SUBDIVISION_LIST_EXTRACTION);
                req.setAttribute(CONTENT, "/pages/laboratory/subdivisions/extractionList.jsp");
                if(role == Role.analyser || role == Role.admin) {
                    req.setAttribute("turnCellulose", Branches.UI.Extraction.TURN_CELLULOSE);
                    req.setAttribute("crudeEdit", Branches.UI.Extraction.CRUDE_EDIT);
                    req.setAttribute("turnProtein", Branches.UI.Extraction.TURN_PROTEIN);
                    req.setAttribute("storageProtein", Branches.UI.Extraction.STORAGE_PROTEIN);
                    req.setAttribute("turnGrease", Branches.UI.Extraction.TURN_GREASE);
                    req.setAttribute("storageGrease", Branches.UI.Extraction.STORAGE_GREASE);
                    req.setAttribute("oilEdit", Branches.UI.Extraction.OIL_EDIT);
                    req.setAttribute("mealGranules", Branches.UI.MEAL_GRANULES);

                }
                req.setAttribute(FIND, Branches.API.FIND_EXTRACTION);
                req.setAttribute("dailyPrint", Branches.UI.Extraction.DAILY_REPORT_PRINT);
                req.setAttribute(SUBSCRIBE, extractionSubscribes);
                break;
            case vro:
                req.setAttribute(TITLE, Titles.SUBDIVISION_LIST_VRO);
                req.setAttribute(CONTENT, "/pages/laboratory/subdivisions/vro/vroList.jsp");
                if(role == Role.admin || role == Role.analyser) {
                    req.setAttribute("crudeEdit", Branches.UI.VRO.CRUDE_EDIT);
                    req.setAttribute("sunProtein", Branches.UI.VRO.SUN_PROTEIN);
                    req.setAttribute("oilEdit", Branches.UI.VRO.OIL_EDIT);
                    req.setAttribute("oilMassFraction", Branches.UI.VRO.OIL_MASS_FRACTION);
                    req.setAttribute("oilMassFractionDry", Branches.UI.VRO.OIL_MASS_FRACTION_DRY);
                    req.setAttribute("granules", Branches.UI.VRO.GRANULES);
                }

                req.setAttribute(FORPRESS, dao.getForpressList());
                req.setAttribute(FIND, Branches.API.FIND_VRO);
                req.setAttribute("dailyEdit", Branches.UI.VRO.DAILY_EDIT);
                req.setAttribute("dailyPrint", Branches.UI.VRO.DAILY_REPORT_PRINT);
                req.setAttribute(SUBSCRIBE, vroSubscribes);
                break;
            case kpo:
                req.setAttribute(TITLE, Titles.SUBDIVISION_LIST_KPO);
                req.setAttribute(CONTENT, "/pages/laboratory/subdivisions/kpoList.jsp");
                if(role == Role.admin || role == Role.analyser) {
                    req.setAttribute(EDIT, Branches.UI.KPO.PART_EDIT);
                }
                req.setAttribute(SUBSCRIBE, kpoSubscribes);
                break;
        }
        req.setAttribute(FILTER, "/pages/laboratory/subdivisions/subdivisionFilter.jsp");
        req.setAttribute(TURNS, TurnBox.getTurns());
        show(req, resp);
    }
}
