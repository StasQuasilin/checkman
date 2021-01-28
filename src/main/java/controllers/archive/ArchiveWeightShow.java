package controllers.archive;

import constants.Branches;
import controllers.IModal;
import entity.transport.Transportation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 02.10.2019.
 */
@WebServlet(Branches.UI.ARCHIVE_WEIGHT_SHOW)
public class ArchiveWeightShow extends IModal {

    public static final String CONTENT = "/pages/archive/weightArchive.jsp";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, WEIGHT_SHOW);
        req.setAttribute(MODAL_CONTENT, CONTENT);
        req.setAttribute(WEIGHT, dao.getObjectById(Transportation.class, Integer.parseInt(req.getParameter(ID))));
        show(req, resp);
    }
}
