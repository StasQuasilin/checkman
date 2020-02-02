package api.laboratory.extraction;

import api.ServletAPI;
import constants.Branches;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.production.Turn;
import entity.production.TurnSettings;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by szpt_user045 on 30.01.2020.
 */
@WebServlet(Branches.API.FIND_EXTRACTION)
public class ExtractionTurnFindAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Date date = Date.valueOf(String.valueOf(body.get(DATE)));
            int turnNumber = Integer.parseInt(String.valueOf(body.get(TURN)));
            JSONArray array = pool.getArray();
            LocalDate localDate = date.toLocalDate();
            if (turnNumber == -1){
                for (TurnSettings t : TurnBox.getTurns()){
                    getData(array, localDate, t.getBegin().toLocalTime());
                }
            } else {
                getData(array, localDate, TurnBox.getTurnByNumber(turnNumber).getBegin().toLocalTime());
            }

            write(resp, array.toJSONString());
            pool.put(array);
        }
    }
    synchronized void getData(JSONArray array, LocalDate localDate, LocalTime localTime){
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(localDate, localTime));
        Turn turnByTime = dao.getTurnByTime(timestamp);
        if (turnByTime != null) {
            ExtractionTurn extractionTurn = dao.getExtractionTurnByTurn(turnByTime);
            if (extractionTurn != null){
                array.add(extractionTurn.toJson());
            }
        }
    }
}
