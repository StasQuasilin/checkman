package controllers.laboratory.subdivisions;

import constants.Branches;
import constants.Constants;
import controllers.IUIServlet;
import entity.SubdivisionKey;
import entity.production.Forpress;

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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SubdivisionKey key = SubdivisionKey.valueOf(req.getParameter("sub"));

        switch (key){
            case ex:
                req.setAttribute("title", Constants.Titles.SUBDIVISION_LIST_EXTRACTION);
                req.setAttribute("content", "/pages/laboratory/subdivisions/extractionList.jsp");
                req.setAttribute("crudeEdit", Branches.UI.Extraction.CRUDE_EDIT);
                req.setAttribute("turnProtein", Branches.UI.Extraction.TURN_PROTEIN);
                req.setAttribute("storageProtein", Branches.UI.Extraction.STORAGE_PROTEIN);
                req.setAttribute("turnGrease", Branches.UI.Extraction.TURN_GREASE);
                req.setAttribute("storageGrease", Branches.UI.Extraction.STORAGE_GREASE);
                req.setAttribute("oilEdit", Branches.UI.Extraction.OIL_EDIT);
                req.setAttribute("update", Branches.API.EXTRACTION_LIST);
                break;
            case vro:
                req.setAttribute("title", Constants.Titles.SUBDIVISION_LIST_VRO);
                req.setAttribute("content", "/pages/laboratory/subdivisions/vro/vroList.jsp");
                req.setAttribute("crudeEdit", Branches.UI.VRO.CRUDE_EDIT);
                req.setAttribute("oilEdit", Branches.UI.VRO.OIL_EDIT);
                req.setAttribute("dailyEdit", Branches.UI.VRO.DAILY_EDIT);
                req.setAttribute("update", Branches.API.VRO_LIST);
                req.setAttribute("forpress", hibernator.query(Forpress.class, null));
                break;
            case kpo:
                req.setAttribute("title", Constants.Titles.SUBDIVISION_LIST_KPO);
                req.setAttribute("content", "/pages/laboratory/subdivisions/kpoList.jsp");
                break;
        }
        show(req, resp);
    }
}
