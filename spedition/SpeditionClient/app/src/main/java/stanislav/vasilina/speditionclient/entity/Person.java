package stanislav.vasilina.speditionclient.entity;

import org.json.simple.JSONObject;

import java.util.Set;

import static stanislav.vasilina.speditionclient.constants.Keys.FORENAME;
import static stanislav.vasilina.speditionclient.constants.Keys.PATRONYMIC;
import static stanislav.vasilina.speditionclient.constants.Keys.SPACE;
import static stanislav.vasilina.speditionclient.constants.Keys.SURNAME;

public class Person extends JsonAble{
    private String surname;
    private String forename;
    private String patronymic;
    private Set<Phone> phones;

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

    public String getPatronymic() {
        return patronymic;
    }
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put(FORENAME, forename);
        json.put(SURNAME, surname);
        json.put(PATRONYMIC, patronymic);
        return json;
    }

    public String getValue() {
        return surname + SPACE + forename;
    }
}
