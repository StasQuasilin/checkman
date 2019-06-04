package api.laboratory;

import api.IAPI;
import constants.Branches;
import controllers.laboratory.laboratory.LaboratoryList;
import entity.Worker;
import entity.laboratory.LaboratoryTurn;
import entity.production.Turn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.turns.TurnBox;
import utils.turns.TurnService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 04.06.2019.
 */
@WebServlet(Branches.API.LABORATORY_TURN_EDIT)
public class LaboratoryTurnEditAPI extends IAPI {

    private final TurnBox turnBox = TurnBox.getBox();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            System.out.println(body);
            final LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            final LocalTime time = turnBox.getTurn((Long) body.get("turn")).getBegin().toLocalTime();
            final LocalDateTime dateTime = LocalDateTime.of(date, time);

            final Turn turn = TurnService.getTurn(turnBox.getTurnDate(dateTime));
            final HashMap<Long, LaboratoryTurn> laboratoryTurns = new HashMap<>();
            for (LaboratoryTurn t : hibernator.query(LaboratoryTurn.class, "turn", turn)){
                laboratoryTurns.put((long) t.getWorker().getId(), t);
            }

            for (Object o : (JSONArray)body.get("personal")){
                JSONObject j = (JSONObject) o;
                long id = (long) j.get("id");

                if (laboratoryTurns.containsKey(id)){
                    laboratoryTurns.remove(id);
                } else {
                    LaboratoryTurn laboratoryTurn = new LaboratoryTurn();
                    laboratoryTurn.setTurn(turn);
                    laboratoryTurn.setWorker(hibernator.get(Worker.class, "id", id));
                    hibernator.save(laboratoryTurn);
                }
            }
            laboratoryTurns.values().forEach(hibernator::remove);

            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }
    }
}
