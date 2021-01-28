package controllers.weight.reporter;

import constants.Branches;
import controllers.IModal;
import entity.Subdivision;
import entity.weight.RoundReport;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.EDIT_REPORT)
public class EditReport extends IModal {
    private static final String _TITLE = "title.report.edit";
    private static final String _CONTENT = "/pages/weight/reporter/reportEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            RoundReport report = dao.getObjectById(RoundReport.class, body.get(ID));
            req.setAttribute(REPORT, report);
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(SUBDIVISIONS, dao.getObjects(Subdivision.class));
        req.setAttribute(SAVE, Branches.API.REPORT_EDIT);
        show(req, resp);
    }
}
