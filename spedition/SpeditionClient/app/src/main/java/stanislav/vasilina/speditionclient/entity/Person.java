package stanislav.vasilina.speditionclient.entity;

import org.json.simple.JSONObject;

import java.util.Set;

import static stanislav.vasilina.speditionclient.constants.Keys.FORENAME;
import static stanislav.vasilina.speditionclient.constants.Keys.ID;
import static stanislav.vasilina.speditionclient.constants.Keys.SPACE;
import static stanislav.vasilina.speditionclient.constants.Keys.SURNAME;

public class Person extends JsonAble{
    private int id;
    private String surname;
    private String forename;
    private Set<Phone> phones;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }
    public void setForename(String forename) {
        this.forename = forename;
    }

    public Set<Phone> getPhones() {
        return phones;
    }
    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put(ID, id);
        json.put(FORENAME, forename);
        json.put(SURNAME, surname);
        return json;
    }

    public String getValue() {
        return surname + SPACE + forename;
    }
}
