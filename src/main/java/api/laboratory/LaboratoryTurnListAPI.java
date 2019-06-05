package api.laboratory;

import api.IAPI;
import constants.Branches;
import entity.laboratory.LaboratoryTurn;
import entity.production.Turn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
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
public class LaboratoryTurnListAPI extends IAPI {

    public static final int LIMIT = 14;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        final JSONObject array = new JSONObject();
        final JSONArray add = new JSONArray();
        final JSONArray update = new JSONArray();
        final JSONArray remove = new JSONArray();
        array.put("add", add);
        array.put("update", update);
        array.put("remove", remove);

        if (body != null) {
            final HashMap<String, Object> parameters = new HashMap<>();
            if (body.containsKey("reqDate")){
                String date = String.valueOf(body.remove("reqDate"));
                LocalDate localDate = LocalDate.parse(date);
                final BETWEEN between = new BETWEEN(Date.valueOf(localDate), Date.valueOf(localDate.plusDays(1)));
                parameters.put("date", between);
            } else {
                final LE le = new LE(Date.valueOf(LocalDate.now()));
                le.setDate(Date.valueOf(LocalDate.now().plusYears(1)));
                parameters.put("date", le);
            }

            for (Turn turn : hibernator.limitQuery(Turn.class, parameters, LIMIT)){
                String id = String.valueOf(turn.getId());
                List<LaboratoryTurn> turnList = hibernator.query(LaboratoryTurn.class, "turn", turn);
                if (body.containsKey(id)){
                    long hash = (long) body.remove(id);
                    int hashCode = turn.hashCode();
                    for (LaboratoryTurn l : turnList){
                        hashCode = 31 * l.getWorker().hashCode() + hashCode;
                    }
                    if (hashCode != hash){
                        update.add(JsonParser.toJson(turn, turnList));
                    }
                } else {
                    add.add(JsonParser.toJson(turn, turnList));
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
