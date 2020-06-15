package ua.svasilina.spedition.entity;

import org.json.simple.JSONObject;

import static ua.svasilina.spedition.constants.Keys.FORENAME;
import static ua.svasilina.spedition.constants.Keys.ID;
import static ua.svasilina.spedition.constants.Keys.PATRONYMIC;
import static ua.svasilina.spedition.constants.Keys.SURNAME;

public class Driver extends JsonAble{
    private String uuid;
    private Person person;

    public Driver() {
    }

    public static Driver fromJson(JSONObject driverJson) {
        if (driverJson != null) {
            Driver driver = new Driver();
            driver.setUuid(String.valueOf(driverJson.get(ID)));
            Person person = new Person();
            person.setSurname(String.valueOf(driverJson.get(SURNAME)));
            person.setForename(String.valueOf(driverJson.get(FORENAME)));
            person.setPatronymic(String.valueOf(driverJson.get(PATRONYMIC)));
            driver.setPerson(person);
            return driver;
        }
        return null;
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
