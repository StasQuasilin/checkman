package controllers.archive;

import constants.Branches;
import controllers.IModal;
import entity.transport.Transportation;
import org.json.simple.JSONObject;

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
    private static final String _TITLE = "title.archive.show";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if (body != null){
            req.setAttribute(WEIGHT, dao.getObjectById(Transportation.class, body.get(ID)));
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(MODAL_CONTENT, CONTENT);

            show(req, resp);
        }
    }
}
