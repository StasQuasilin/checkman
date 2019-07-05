package controllers.laboratory.probe;

import constants.Branches;
import constants.Constants;
import controllers.IUIServlet;
import entity.AnalysesType;
import entity.products.Product;
import utils.hibernate.State;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by szpt_user045 on 25.03.2019.
 */
@WebServlet(Branches.UI.PROBE_LIST)
public class ProbeList extends IUIServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Constants.Titles.PROBE_LIST);
        req.setAttribute("content", "/pages/laboratory/probeList.jsp");
        req.setAttribute("update", Branches.API.PROBE_LIST);
        req.setAttribute("show", Branches.UI.PROBE_SHOW);
        req.setAttribute("edit", Branches.UI.PROBE_EDIT);
        req.setAttribute("analysesTypes", AnalysesType.values());
        req.setAttribute("filter", "/pages/filters/archiveFilter.jsp");
        show(req, resp);
    }
}
