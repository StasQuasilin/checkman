package api.laboratory;

import api.API;
import constants.Branches;
import entity.laboratory.LaboratoryTurn;
import entity.production.Turn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.JsonPool;
import utils.hibernate.DateContainers.BETWEEN;
import utils.hibernate.DateContainers.LE;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 05.06.2019.
 */
@WebServlet(Branches.API.LABORATORY_TURN_LIST)
public class LaboratoryTurnListAPI extends API {

    final JsonPool pool = JsonPool.getPool();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        final JSONObject array = pool.getObject();
        final JSONArray add = pool.getArray();
        final JSONArray update = pool.getArray();
        final JSONArray remove = pool.getArray();
        array.put(ADD, add);
        array.put(UPDATE, update);
        array.put(REMOVE, remove);

        if (body != null) {
            List<Turn> turns;
            if (body.containsKey("reqDate")){
                String date = String.valueOf(body.remove("reqDate"));
                LocalDate localDate = LocalDate.parse(date);
                turns = dao.getTurnsBetween(localDate, localDate.plusDays(1));
            } else {
                turns = dao.getLimitTurns();
            }

            for (Turn turn : turns){
                String id = String.valueOf(turn.getId());
                List<LaboratoryTurn> turnList = dao.getLaboratoryTurnByTurn(turn);
                if (turnList.size() > 0) {
                    if (body.containsKey(id)) {
                        long hash = (long) body.remove(id);
                        int hashCode = turn.hashCode();
                        for (LaboratoryTurn l : turnList) {
                            hashCode = 31 * l.getWorker().hashCode() + hashCode;
                        }
                        if (hashCode != hash) {
                            update.add(JsonParser.toJson(turn, turnList));
                        }
                    } else {
                        add.add(JsonParser.toJson(turn, turnList));
                    }
                }
            }

            for (Object o : body.keySet()){
                remove.add(Integer.parseInt(String.valueOf(o)));
            }
        }
        write(resp, array.toJSONString());
        add.clear();
        update.clear();
        remove.clear();
    }
}
