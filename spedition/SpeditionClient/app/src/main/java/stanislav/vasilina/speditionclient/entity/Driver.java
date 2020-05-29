package stanislav.vasilina.speditionclient.entity;

import org.json.simple.JSONObject;

import static stanislav.vasilina.speditionclient.constants.Keys.ID;
import static stanislav.vasilina.speditionclient.constants.Keys.PERSON;

public class Driver extends JsonAble{
    private int id;
    private Person person;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put(ID, id);
        json.put(PERSON, person.toJson());
        return json;
    }

    public String getValue() {
        return person.getValue();
    }
}
