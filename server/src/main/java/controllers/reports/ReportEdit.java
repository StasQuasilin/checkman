package controllers.reports;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.reports.ManufactureReport;
import entity.reports.ReportFieldCategory;
import entity.reports.ReportFieldSettings;
import org.json.simple.JSONObject;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
@WebServlet(Branches.UI.MANUFACTURE_REPORT_EDIT)
public class ReportEdit extends IModal {

    private static final String _TITLE = "title.manufacture.reports.edit";
    private static final String _CONTENT = "/pages/reports/manufactureReportEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = -1;
        String parameter = req.getParameter(ID);
        if (parameter != null){
            id = Integer.parseInt(parameter);
        } else {
            JSONObject body = parseBody(req);
            if (body != null && body.containsKey(ID)){
                id = Integer.parseInt(String.valueOf(body.get(ID)));
            }
        }

        req.setAttribute(CATEGORIES, dao.getObjects(ReportFieldCategory.class));

        if (id != -1){
            ManufactureReport report = dao.getObjectById(ManufactureReport.class, id);
            req.setAttribute(REPORT, report);
            req.setAttribute(FIELDS, report.getFields());
        } else {
            ArrayList<ReportFieldSettings> settings = new ArrayList<>(dao.getObjects(ReportFieldSettings.class));
            settings.sort((o1, o2) -> o1.getIndex() - o2.getIndex());
            for (ReportFieldSettings s : settings){
                s.setId(-1);
            }
            req.setAttribute(FIELDS, settings);
        }

        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(TURNS, TurnBox.getTurns());
        req.setAttribute(UNITS, dao.getWeightUnits());
        req.setAttribute(PRODUCTS, dao.getProductList());
        req.setAttribute(SAVE, Branches.API.SAVE_MANUFACTURE_REPORT);
        req.setAttribute(PREVIEW, Branches.UI.MANUFACTURE_REPORT_PREVIEW);
        show(req, resp);
    }
}
