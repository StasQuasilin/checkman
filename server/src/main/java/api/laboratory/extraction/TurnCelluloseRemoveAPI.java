package api.laboratory.extraction;

import api.ServletAPI;
import constants.Branches;
import entity.laboratory.subdivisions.extraction.TurnCellulose;
import org.json.simple.JSONObject;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.API.EXTRACTION_TURN_CELLULOSE_REMOVE)
public class TurnCelluloseRemoveAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            TurnCellulose cellulose = dao.getObjectById(TurnCellulose.class, body.get(ID));
            dao.remove(cellulose, cellulose.getCreateTime());
            write(resp, SUCCESS_ANSWER);
            updateUtil.onSave(dao.getExtractionTurnByTurn(cellulose.getTurn().getTurn()));
        }
    }
}
