package ua.quasilin.chekmanszpt.entity;

import org.json.simple.JSONObject;

/**
 * Created by szpt_user045 on 05.08.2019.
 */

public class Worker {
    private long id;
    private Person person;

    public Worker(Object object) {
        JSONObject json = (JSONObject) object;
        id = (long) json.get("id");
        person = new Person(json.get("person"));
    }

    public long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public String getValue() {
        return person.getValue();
    }
}
