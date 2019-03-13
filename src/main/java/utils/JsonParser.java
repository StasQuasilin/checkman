package utils;

import entity.answers.IAnswer;
import entity.documents.Deal;
import entity.organisations.Organisation;
import org.json.simple.JSONObject;

import java.util.Map;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
public class JsonParser {
    public static JSONObject toJson(Organisation organisation) {
        JSONObject json = new JSONObject();
        json.put("id", organisation.getId());
        json.put("status", organisation.getType().getName());
        json.put("name", organisation.getName());
        return json;
    }

    public static JSONObject toJson(Deal deal) {
        JSONObject json = new JSONObject();
        json.put("id", deal.getId());
        json.put("date", deal.getDate().toString());
        json.put("date_to", deal.getDateTo().toString());
        json.put("organisation", toJson(deal.getOrganisation()));
        return json;
    }

    public static JSONObject toJson(IAnswer answer) {
        JSONObject json = new JSONObject();
        json.put("status", answer.status());
        for (Map.Entry<String, String> entry : answer.getParams().entrySet()){
            json.put(entry.getKey(), entry.getValue());
        }
        return json;
    }
}
