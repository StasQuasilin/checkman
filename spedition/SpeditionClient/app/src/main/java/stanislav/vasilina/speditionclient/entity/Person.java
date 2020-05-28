package stanislav.vasilina.speditionclient.entity;

import java.util.Set;

public class Person {
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
}
