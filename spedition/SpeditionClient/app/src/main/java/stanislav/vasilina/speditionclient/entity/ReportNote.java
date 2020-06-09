package stanislav.vasilina.speditionclient.entity;

import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.Calendar;

import static stanislav.vasilina.speditionclient.constants.Keys.ID;
import static stanislav.vasilina.speditionclient.constants.Keys.NOTE;
import static stanislav.vasilina.speditionclient.constants.Keys.TIME;

public class ReportNote extends JsonAble implements Serializable {
    private String uuid;
    private Calendar time;
    private String note;

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Calendar getTime() {
        return time;
    }
    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, uuid);
        jsonObject.put(TIME, time.getTimeInMillis());
        jsonObject.put(NOTE, note);
        return jsonObject;
    }
}
