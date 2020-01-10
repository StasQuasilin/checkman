package controllers.reports;

import constants.Branches;
import controllers.IModal;
import entity.reports.ManufactureReport;
import entity.reports.ReportField;
import entity.reports.ReportFieldCategory;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 07.11.2019.
 */
@WebServlet(Branches.UI.MANUFACTURE_REPORT_PREVIEW)
public class ReportPreview extends IModal {

    private static final String _TITLE = "title.report.preview";
    private static final String _CONTENT = "/pages/reports/reportPreview.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null){
            if (body.containsKey(ID)){
                ManufactureReport report = dao.getObjectById(ManufactureReport.class, body.get(ID));
                HashMap<ReportFieldCategory, ArrayList<ReportField>> fields = new HashMap<>();
                report.getFields().sort((o1, o2) -> o1.getIndex() - o2.getIndex());
                for (ReportField reportField : report.getFields()){
                    if (!fields.containsKey(reportField.getCategory())){
                        fields.put(reportField.getCategory(), new ArrayList<>());
                    }
                    fields.get(reportField.getCategory()).add(reportField);
                }
                ArrayList<ReportFieldCategory> categories = new ArrayList<>(fields.keySet());
                categories.sort((o1, o2) -> {
                    if (o1 == null){
                        return -1;
                    } else if (o2 == null){
                        return 1;
                    } else {
                        return o1.getNumber() - o2.getNumber();
                    }
                });
                req.setAttribute(CATEGORIES, categories);
                req.setAttribute("noCategory", fields.get(null));
                req.setAttribute(FIELDS, fields);
                req.setAttribute(REPORT, report);
            }
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(DELETE, Branches.API.MANUFACTURE_REPORT_REMOVE);
        req.setAttribute(EDIT, Branches.UI.MANUFACTURE_REPORT_EDIT);
        req.setAttribute(SEND, Branches.API.MANUFACTURE_REPORT_SEND);
        show(req, resp);
    }
}
