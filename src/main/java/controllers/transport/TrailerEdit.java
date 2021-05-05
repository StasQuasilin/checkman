package controllers.transport;

import constants.Branches;
import controllers.IModal;
import entity.transport.Trailer;
import org.json.simple.JSONObject;
import utils.hibernate.dao.HibernateDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.TRAILER_EDIT)
public class TrailerEdit extends IModal {
    private static final String _CONTENT = "/pages/transport/trailerEdit.jsp";
    private static final String _TITLE = "title.trailer.edit";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if (body != null){
            final Trailer trailer = dao.getObjectById(Trailer.class, body.get(ID));
            req.setAttribute(TRAILER, trailer);
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(SAVE, Branches.API.TRAILER_EDIT);
        show(req, resp);
    }
}
