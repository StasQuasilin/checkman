package controllers.transport;

import constants.Branches;
import controllers.IModal;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.hibernate.HibernateDAO;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.CUSTOM_TIME)
public class CustomTime extends IModal {
    private static final String _TITLE = "title.custom.date";
    private static final String _CONTENT = "/pages/transport/customTime.jsp";

    private final dbDAO dao = dbDAOService.getDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            final Transportation tra = dao.getObjectById(Transportation.class, body.get(ID));
            req.setAttribute(ACTION, body.get(ACTION));
            req.setAttribute(TRANSPORTATION, tra);
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(MODAL_CONTENT, _CONTENT);
            req.setAttribute(SAVE, Branches.API.SAVE_CUSTOM_TIME);
            show(req, resp);
        }
    }
}
