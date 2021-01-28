package controllers.board;

import constants.Branches;
import controllers.IModal;
import entity.border.BoardItem;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.BOARD_EDIT)
public class BoardEdit extends IModal {

    private static final String _TITLE = "title.board.edit";
    private static final String _CONTENT = "/pages/board/boardEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            BoardItem item = dao.getObjectById(BoardItem.class, body.get(ID));
            req.setAttribute(ITEM, item);
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(SAVE, Branches.API.BOARD_EDIT);
        show(req, resp);
    }
}
