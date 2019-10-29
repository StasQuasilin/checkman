package api.laboratory;

import api.ServletAPI;
import constants.Branches;
import entity.laboratory.turn.LaboratoryTurn;
import entity.laboratory.turn.LaboratoryTurnWorker;
import entity.production.Turn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.UpdateUtil;
import utils.turns.TurnBox;
import utils.turns.TurnService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 04.06.2019.
 */
@WebServlet(Branches.API.LABORATORY_TURN_EDIT)
public class LaboratoryTurnEditServletAPI extends ServletAPI {

    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            System.out.println(body);
            final LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            final LocalTime time = TurnBox.getTurnByNumber(Integer.parseInt(String.valueOf(body.get("turn"))))
                    .getBegin().toLocalTime();
            final LocalDateTime dateTime = LocalDateTime.of(date, time);

            final Turn turn = TurnService.getTurn(TurnBox.getTurnDate(dateTime));
            LaboratoryTurn laboratoryTurn = dao.getLaboratoryTurnByTurn(turn);
            if (laboratoryTurn == null) {
                laboratoryTurn = new LaboratoryTurn();
                laboratoryTurn.setTurn(turn);
            }

            final ArrayList<LaboratoryTurnWorker> workers = new ArrayList<>();
            final HashMap<Integer, LaboratoryTurnWorker> currentWorkers = new HashMap<>();
            for (LaboratoryTurnWorker w : laboratoryTurn.getWorkers()){
                currentWorkers.put(w.getId(), w);
            }

            for (Object o : (JSONArray)body.get("personal")){
                JSONObject j = (JSONObject) o;
                long id = (long) j.get("id");
                if (currentWorkers.containsKey(id)){

                } else {
                    LaboratoryTurnWorker worker = new LaboratoryTurnWorker();
                    worker.setTurn(laboratoryTurn);
                    worker.setWorker(dao.getWorkerById(id));
                    workers.add(worker);
                }
            }
            dao.save(laboratoryTurn);
            for (LaboratoryTurnWorker worker : workers){
                dao.save(worker);
            }
            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
