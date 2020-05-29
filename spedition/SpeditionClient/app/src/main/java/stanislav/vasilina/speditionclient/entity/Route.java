package stanislav.vasilina.speditionclient.entity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

import static stanislav.vasilina.speditionclient.constants.Keys.ROUTE;

public class Route extends JsonAble {

    private ArrayList<String> points = new ArrayList<>();
    public static final String ARROW = "➞";

    public String getValue(){
        if (points.size() > 0){
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < points.size(); i++){
                builder.append(points.get(i).toUpperCase());
                if (i < points.size() - 1){
                    builder.append(ARROW);
                }
            }
            return builder.toString();
        }
        return "->->->";
    }

    public void addPoint(String item) {
        points.add(item);
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(ROUTE, route());
        return jsonObject;
    }

    private JSONArray route() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < points.size(); i++){
            array.add(points.get(i));
        }
        return array;
    }
}
