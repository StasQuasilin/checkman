package controllers.seals;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import controllers.IUIServlet;
import entity.seals.SealBatch;
import org.json.simple.JSONObject;
import utils.hibernate.HibernateDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.SEAL_SHOW)
public class SealsShow extends IModal {

    private static final String _CONTENT = "/pages/seals/sealsShow.jsp";
    private static final String _TITLE = "title.seal.show";
    private final HibernateDAO dao = new HibernateDAO();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            final Object id = body.get(ID);
            req.setAttribute(SEALS, dao.getObjectById(SealBatch.class, id));
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(MODAL_CONTENT, _CONTENT);
            req.setAttribute(Constants.REMOVE, Branches.UI.SEALS_REMOVE);
            show(req, resp);
        }
    }
}
