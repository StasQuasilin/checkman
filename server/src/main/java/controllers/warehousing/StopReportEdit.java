package controllers.warehousing;

import constants.Branches;
import controllers.IModal;
import entity.Subdivision;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 10.12.2019.
 */
@WebServlet(Branches.UI.STOP_REPORT)
public class StopReportEdit extends IModal {

    private static final String _TITLE = "title.stop.report";
    private static final String _CONTENT = "/pages/warehousing/stopReport.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(SAVE, Branches.API.EDIT_STOP_REPORT);
        req.setAttribute(SUBDIVISIONS, dao.getObjects(Subdivision.class));
        show(req, resp);
    }
}
