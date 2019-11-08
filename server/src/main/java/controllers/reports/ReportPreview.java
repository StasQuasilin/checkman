package controllers.reports;

import constants.Branches;
import controllers.IModal;
import controllers.IUIServlet;
import entity.reports.ManufactureReport;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                req.setAttribute(REPORT, dao.getObjectById(ManufactureReport.class, body.get(ID)));
            }
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(DELETE, Branches.UI.MANUFACTURE_REPORT_REMOVE);
        req.setAttribute(EDIT, Branches.UI.MANUFACTURE_REPORT_EDIT);
        req.setAttribute(SEND, Branches.API.MANUFACTURE_REPORT_SEND);
        show(req, resp);
    }
}
