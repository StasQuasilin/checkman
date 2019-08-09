package ua.quasilin.chekmanszpt.entity;

import android.support.annotation.NonNull;

import org.json.simple.JSONObject;

/**
 * Created by szpt_user045 on 05.08.2019.
 */

public class Person implements Comparable<Person>{
    private long id;
    private String surname;
    private String forename;
    private String patronymic;

    Person(Object o) {
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

    String getValue() {
        return surname + " " + forename;
    }

    @Override
    public int compareTo(@NonNull Person o) {
        int compare = surname.compareTo(o.surname);
        if (compare == 0){
            compare = forename.compareTo(o.forename);
        }
        if (compare == 0){
            compare = patronymic.compareTo(o.patronymic);
        }
        return compare;
    }
}
