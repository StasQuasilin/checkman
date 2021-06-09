package controllers.admin;

import constants.Branches;
import controllers.IModal;
import entity.Role;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.CHANGE_VIEW)
public class ChangeView extends IModal {

    private static final String _TITLE = "title.change.role";
    private static final String _CONTENT = "/pages/admin/changeView.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Role role = getRole(req);
        if (role == Role.admin){
            req.setAttribute(ROLES, Role.values());
            req.setAttribute(SAVE, Branches.API.CHANGE_ROLE);
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(MODAL_CONTENT, _CONTENT);
            show(req, resp);
        }
    }
}
