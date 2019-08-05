package ua.quasilin.chekmanszpt.entity;

import org.json.simple.JSONObject;

/**
 * Created by szpt_user045 on 05.08.2019.
 */

public class Person {
    private long id;
    private String surname;
    private String forename;
    private String patronymic;

    public Person(Object o) {
        JSONObject json = (JSONObject) o;
        id = (long) json.get("id");
        surname = String.valueOf(json.get("surname"));
        forename = String.valueOf(json.get("forename"));
        patronymic = String.valueOf(json.get("patronymic"));
    }

    public long getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getForename() {
        return forename;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getValue() {
        return surname + " " + forename;
    }
}
