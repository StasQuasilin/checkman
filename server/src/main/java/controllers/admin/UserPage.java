package controllers.admin;

import constants.Branches;
import controllers.IModal;
import entity.Role;
import entity.User;
import entity.Worker;
import org.json.simple.JSONObject;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.USER_PAGE)
public class UserPage extends IModal {
    private static final String _TITLE = "title.user.page";
    private static final String _CONTENT = "/pages/admin/userPage.jsp";
    private final dbDAO dao = dbDAOService.getDAO();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            final Worker worker = dao.getObjectById(Worker.class, body.get(ID));
            final User user = dao.getUserByWorker(worker);
            if(user != null) {
                req.setAttribute(PASSWORD, user.getPassword());
            }
            req.setAttribute(WORKER, worker);
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(MODAL_CONTENT, _CONTENT);
            req.setAttribute(SAVE, Branches.API.SAVE_USER_DATA);
            req.setAttribute(ROLES, Role.values());
            req.setAttribute(DELETE, Branches.API.USER_DELETE);
            show(req, resp);
        }
    }
}
