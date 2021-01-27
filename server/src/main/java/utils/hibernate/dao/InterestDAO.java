package utils.hibernate.dao;

import constants.Constants;
import entity.interest.Interest;
import entity.interest.InterestType;
import entity.Worker;
import entity.organisations.Organisation;
import entity.products.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InterestDAO extends HibernateDAO{

    public void initMap(Worker worker, HashMap<InterestType, HashMap<Integer, Interest>> map) {
        for(Interest interest : hibernator.query(Interest.class, Constants.WORKER, worker)){
            final InterestType interestType = interest.getInterestType();
            if (!map.containsKey(interestType)){
                map.put(interestType, new HashMap<>());
            }
            final HashMap<Integer, Interest> hashMap = map.get(interestType);
            hashMap.put(interest.getInterestId(), interest);
        }
    }

    public JSONObject buildInterestJson(Worker worker) {
        final HashMap<InterestType, HashMap<Integer, Interest>> map = new HashMap<>();
        initMap(worker, map);

        JSONObject json = new JSONObject();

        for (Map.Entry<InterestType, HashMap<Integer, Interest>> entry : map.entrySet()){
            final InterestType key = entry.getKey();
            final HashMap<Integer, Interest> value = entry.getValue();

            final Interest all = value.remove(-1);
            json.put(Constants.ALL, all != null);

            final JSONObject object = new JSONObject();
            final Interest settings = value.remove(-2);
            if (settings != null){
                object.put(Constants.I, settings.isI());
                object.put(Constants.W, settings.isW());
                object.put(Constants.A, settings.isA());
                object.put(Constants.O, settings.isO());
            }

            final JSONArray array = new JSONArray();
            for(Map.Entry<Integer, Interest> interestEntry : value.entrySet()){
                insertInterestItem(key, interestEntry.getKey(), array);
            }
            object.put(Constants.ITEMS, array);
            json.put(key.toString(), object);
        }

        return json;
    }

    void insertInterestItem(InterestType type, int id, JSONArray array){
        switch (type){
            case counterparty:
                array.add(getObjectById(Organisation.class, id).toJson());
                break;
            case product:
                array.add(getObjectById(Product.class, id).toJson());
        }
    }
}
