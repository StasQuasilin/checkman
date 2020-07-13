package controllers.admin;

import api.sockets.Subscriber;
import constants.Branches;
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

    private static final long serialVersionUID = 3036740553163445206L;
    private final Subscriber[] subscribers = new Subscriber[]{Subscriber.USERS};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, "title.user.list");
        req.setAttribute(SUBSCRIBE, subscribers);
        req.setAttribute(MODAL_CONTENT, "/pages/admin/userList.jsp");
        req.setAttribute("userPage", Branches.UI.USER_PAGE);
        show(req, resp);
    }
}
