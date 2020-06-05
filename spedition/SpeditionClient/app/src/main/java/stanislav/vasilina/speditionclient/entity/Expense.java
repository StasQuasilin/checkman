package stanislav.vasilina.speditionclient.entity;

import androidx.annotation.NonNull;

import org.json.simple.JSONObject;

import static stanislav.vasilina.speditionclient.constants.Keys.AMOUNT;
import static stanislav.vasilina.speditionclient.constants.Keys.COLON;
import static stanislav.vasilina.speditionclient.constants.Keys.DESCRIPTION;
import static stanislav.vasilina.speditionclient.constants.Keys.ID;
import static stanislav.vasilina.speditionclient.constants.Keys.SPACE;

public class Expense extends JsonAble{
    private String uuid;
    private String description;
    private int amount;

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject json = new JSONObject();
        json.put(ID, uuid);
        json.put(DESCRIPTION, description);
        json.put(AMOUNT, amount);
        return json;
    }

    @NonNull
    @Override
    public String toString() {
        return description + COLON + SPACE + amount;
    }
}
