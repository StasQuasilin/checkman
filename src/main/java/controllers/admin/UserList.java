package controllers.admin;

import api.sockets.Subscribe;
import constants.Branches;
import constants.Constants;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
@WebServlet(Branches.UI.USER_LIST)
public class UserList extends IModal {

    private final Subscribe[] subscribes = new Subscribe[]{Subscribe.USERS};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, "title.user.list");
        req.setAttribute(SUBSCRIBE, subscribes);
        req.setAttribute(MODAL_CONTENT, "/pages/admin/userList.jsp");
        req.setAttribute("userPage", Branches.UI.USER_PAGE);
        req.setAttribute(Constants.KILL_SESSION, Branches.API.KILL_SESSION);
        show(req, resp);
    }
}
