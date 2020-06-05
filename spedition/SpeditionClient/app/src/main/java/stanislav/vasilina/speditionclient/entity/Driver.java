package stanislav.vasilina.speditionclient.entity;

import org.json.simple.JSONObject;

import static stanislav.vasilina.speditionclient.constants.Keys.ID;
import static stanislav.vasilina.speditionclient.constants.Keys.PERSON;

public class Driver extends JsonAble{
    private String uuid;
    private Person person;

    public Driver() {
    }

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = person.toJson();
        json.put(ID, uuid);
        return json;
    }

    public String getValue() {
        return person.getValue();
    }
}
