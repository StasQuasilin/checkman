package api.board;

import api.ServletAPI;
import constants.Branches;
import entity.border.BoardItem;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet(Branches.API.BOARD_EDIT)
public class BoardEditAPI extends ServletAPI{

    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            BoardItem boardItem = dao.getObjectById(BoardItem.class, body.get(ID));
            if (boardItem == null){
                boardItem = new BoardItem();
            }
            String title = String.valueOf(body.get(TITLE));
            boardItem.setTitle(title);

            String text = String.valueOf(body.get(TEXT));
            boardItem.setText(text);
            ActionTime created = boardItem.getCreated();
            if (created == null){
                created = new ActionTime();
                created.setCreator(getWorker(req));
                boardItem.setCreated(created);
            }

            created.setTime(Timestamp.valueOf(LocalDateTime.now()));
            dao.save(created);
            dao.save(boardItem);

            write(resp, SUCCESS_ANSWER);

            updateUtil.onSave(boardItem);

        }
    }
}
