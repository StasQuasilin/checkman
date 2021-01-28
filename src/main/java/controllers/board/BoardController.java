package controllers.board;

import api.sockets.Subscriber;
import com.google.common.eventbus.Subscribe;
import constants.Branches;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.BOARD)
public class BoardController extends IUIServlet {
    private static final String _TITLE = "title.board";
    private static final String _CONTENT = "/pages/board/board.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(EDIT, Branches.UI.BOARD_EDIT);
        req.setAttribute(REMOVE, Branches.API.BOARD_REMOVE);
        req.setAttribute(SUBSCRIBE, new Subscriber[]{Subscriber.BOARD});
        show(req, resp);
    }
}
