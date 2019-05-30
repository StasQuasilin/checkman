package api.rails;

import api.IAPI;
import constants.Branches;
import entity.answers.IAnswer;
import entity.rails.Train;
import entity.rails.Truck;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 30.05.2019.
 */
@WebServlet(Branches.API.RAILS_SAVE)
public class EditRailAPI extends IAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long id = -1;
            if (body.containsKey("id")){
                id = (long) body.get("id");
            }
            Truck truck;
            if (id != -1) {
                truck = hibernator.get(Truck.class, "id", id);
            } else {
                truck = new Truck();
            }
            truck.setTrain(hibernator.get(Train.class, "id", body.get("train")));
            truck.setNumber(String.valueOf(body.get("number")));
            hibernator.save(truck);
            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }
    }
}
