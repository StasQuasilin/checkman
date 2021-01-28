package api.personal;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.interest.InterestType;
import entity.interest.Interest;
import entity.Worker;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.hibernate.dao.InterestDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(Branches.API.INTEREST_SAVE)
public class InterestSaveApi extends ServletAPI {

    private final InterestDAO interestDAO = new InterestDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if (body != null){
            final Worker worker = getWorker(req);
            final HashMap<InterestType, HashMap<Integer, Interest>> map = new HashMap<>();
            interestDAO.initMap(worker, map);

            final boolean all = Boolean.parseBoolean(String.valueOf(body.get(Constants.ALL)));
            saveInterest(all, InterestType.counterparty, map, (JSONObject) body.get(Constants.COUNTERPARTY), worker);
        }
        write(resp, SUCCESS_ANSWER);
    }

    private void saveInterest(boolean all, InterestType type, HashMap<InterestType, HashMap<Integer, Interest>> map, JSONObject object, Worker user) {

        HashMap<Integer, Interest> hashMap = map.get(type);
        if (hashMap == null){
            hashMap = new HashMap<>();
        }

        if (all){
            saveInterest(-1, hashMap, type, user, object); //save if all
        }

        saveInterest(-2, hashMap, type, user, object);

        for (Object o : (JSONArray)object.get(Constants.ITEMS)){
            JSONObject obj = (JSONObject) o;
            int id = Integer.parseInt(String.valueOf(obj.get(Constants.ITEM_ID)));
            saveInterest(id, hashMap, type, user, obj);
        }

        for (Interest r : hashMap.values()){
            interestDAO.remove(r);
        }
    }

    private void saveInterest(int id, HashMap<Integer, Interest> map, InterestType type, Worker user, JSONObject obj) {
        Interest interest = map.remove(id);
        if (interest == null){
            interest = new Interest();
            interest.setWorker(user);
            interest.setInterestType(type);
            interest.setInterestId(id);
        }

        interest.setI(getBool(obj, Constants.I));
        interest.setW(getBool(obj, Constants.W));
        interest.setA(getBool(obj, Constants.A));
        interest.setO(getBool(obj, Constants.O));

        interestDAO.save(interest);
    }

    private boolean getBool(JSONObject obj, String key) {
        if (obj.containsKey(key)){
            return Boolean.parseBoolean(String.valueOf(obj.get(key)));
        }
        return false;
    }
}
