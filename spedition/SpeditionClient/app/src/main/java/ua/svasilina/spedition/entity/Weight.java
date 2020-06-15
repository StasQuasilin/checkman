package ua.svasilina.spedition.entity;

import org.json.simple.JSONObject;

import static ua.svasilina.spedition.constants.Keys.GROSS;
import static ua.svasilina.spedition.constants.Keys.ID;
import static ua.svasilina.spedition.constants.Keys.TARE;

public class Weight extends JsonAble{
    private int id;
    private float gross;
    private float tare;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public float getGross() {
        return gross;
    }
    public void setGross(float gross) {
        this.gross = gross;
    }

    public float getTare() {
        return tare;
    }
    public void setTare(float tare) {
        this.tare = tare;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(GROSS, gross);
        jsonObject.put(TARE, tare);
        return jsonObject;
    }

    public float getNet() {
        if(gross > 0 && tare > 0){
            return gross - tare;
        }
        return 0;
    }
}
