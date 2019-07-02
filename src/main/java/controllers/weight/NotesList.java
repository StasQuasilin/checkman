package controllers.weight;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.documents.LoadPlan;
import org.json.simple.JSONObject;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 02.07.2019.
 */
@WebServlet(Branches.UI.NOTES_LIST)
public class NotesList extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        if (body != null) {
            Object id = body.get(Constants.ID);
            LoadPlan loadPlan = dao.getLoadPlanById(id);
            req.setAttribute("plan", loadPlan);
            req.setAttribute("title", "title.notes.edit");
            req.setAttribute("modalContent", "/pages/transport/notesList.jsp");
            req.setAttribute("update", Branches.API.TRANSPORT_NOTES_LIST);
            req.setAttribute("save", Branches.API.SAVE_NOTE);
            req.setAttribute("remove", Branches.API.REMOVE_NOTE);
            show(req, resp);

        }
    }
}
