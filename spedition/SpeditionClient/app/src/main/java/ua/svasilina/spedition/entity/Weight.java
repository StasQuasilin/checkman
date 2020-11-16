package ua.svasilina.spedition.entity;

import org.json.simple.JSONObject;

import static ua.svasilina.spedition.constants.Keys.GROSS;
import static ua.svasilina.spedition.constants.Keys.ID;
import static ua.svasilina.spedition.constants.Keys.TARE;

public class Weight implements JsonAble {
    private int id;
    private int gross;
    private int tare;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getGross() {
        return gross;
    }
    public void setGross(int gross) {
        this.gross = gross;
    }

    public int getTare() {
        return tare;
    }
    public void setTare(int tare) {
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
