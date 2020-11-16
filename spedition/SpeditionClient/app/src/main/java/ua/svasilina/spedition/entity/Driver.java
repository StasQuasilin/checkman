package ua.svasilina.spedition.entity;

import org.json.simple.JSONObject;

import static ua.svasilina.spedition.constants.Keys.FORENAME;
import static ua.svasilina.spedition.constants.Keys.ID;
import static ua.svasilina.spedition.constants.Keys.PATRONYMIC;
import static ua.svasilina.spedition.constants.Keys.SURNAME;

public class Driver implements JsonAble {
    private long id;
    private int serverId;
    private String uuid;
    private Person person;

    public Driver() {}

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

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
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

    public boolean isEmpty() {
        return person.isEmpty();
    }

    public void addPhone(String phone) {
        person.addPhone(phone);
    }

    public boolean anyChanges() {
        if (person != null){
            return person.anyChanges();
        }
        return false;
    }
}
