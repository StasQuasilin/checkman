package controllers.reports;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
@WebServlet(Branches.UI.MANUFACTURE_REPORT_EDIT)
public class ReportEdit extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = -1;
        String parameter = req.getParameter(Constants.ID);
        if (parameter != null){
            id = Integer.parseInt(parameter);
        } else {
            JSONObject body = PostUtil.parseBodyJson(req);
            if (body != null && body.containsKey(Constants.ID)){
                id = Integer.parseInt((String) body.get(Constants.ID));
            }
        }

        if (id != -1){
            req.setAttribute("report", dao.getManufactureReport(id));
        }

        req.setAttribute("title", "title.manufacture.reports.edit");
        req.setAttribute("modalContent", "/pages/reports/manufactureReportEdit.jsp");
        req.setAttribute("turns", TurnBox.getTurns());
        req.setAttribute("fields", dao.getReportFields());
        req.setAttribute("units", dao.getWeightUnits());
        req.setAttribute("storages", dao.getStorages());
        req.setAttribute("categories", dao.getReportCategories());
        req.setAttribute("save", Branches.API.SAVE_MANUFACTURE_REPORT);
        show(req, resp);
    }
}
