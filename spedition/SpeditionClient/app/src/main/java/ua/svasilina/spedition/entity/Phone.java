package ua.svasilina.spedition.entity;

public class Phone {
    private int id;
    private Person person;
    private String number;

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

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
}
