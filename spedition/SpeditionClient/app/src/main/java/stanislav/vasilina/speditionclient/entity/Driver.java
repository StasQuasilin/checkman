package stanislav.vasilina.speditionclient.entity;

public class Driver {
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
}
