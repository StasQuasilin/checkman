package entity.deals;

import entity.ActionTime;
import entity.references.Organisation;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import java.sql.Date;
import static constants.Keys.ID;

public class Deal extends JsonAble {
    private int id;
    private DealType type;
    private String number;
    private Date date;
    private Date from;
    private Date to;
    private Organisation counterparty;
    private ActionTime created;
    private boolean archive;

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        return jsonObject;
    }
}
