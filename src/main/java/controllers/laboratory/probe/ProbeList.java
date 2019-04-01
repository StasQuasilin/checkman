package controllers.laboratory.probe;

import constants.Branches;
import constants.Constants;
import controllers.IUIServlet;
import entity.Product;
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
        req.setAttribute("updateURL", Branches.API.PROBE_LIST);
        req.setAttribute("showURL", Branches.UI.PROBE_SHOW);
        req.setAttribute("editURL", Branches.UI.PROBE_EDIT);
        List<String> analyses = new LinkedList<>();
        for (Product p : hibernator.query(Product.class, "analysesType", State.notNull)){
            String a = p.getAnalysesType().toString();
            if (!analyses.contains(a)){
                analyses.add(a);
            }
        }
        req.setAttribute("analysesTypes", analyses);
        req.setAttribute("filter", "/pages/filters/archiveFilter.jsp");
        show(req, resp);
    }
}
