package controllers.seals;

import constants.Branches;
import controllers.IModal;
import entity.seals.SealBatch;
import org.json.simple.JSONObject;
import utils.hibernate.DeprecatedDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.SEALS_REMOVE)
public class SealsRemove extends IModal {

    private static final String _TITLE = "title.seals.remove";
    private static final String _CONTENT = "/pages/seals/removeSeals.jsp";
    private final DeprecatedDAO dao = new DeprecatedDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if (body != null){
            final Object id = body.get(ID);
            final SealBatch batch = dao.getObjectById(SealBatch.class, id);
            req.setAttribute(SEALS, batch);
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(MODAL_CONTENT, _CONTENT);
            req.setAttribute(REMOVE, Branches.API.SEALS_REMOVE);
            show(req, resp);
        }
    }
}
