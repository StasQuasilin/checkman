package entity.transport;

import javax.persistence.*;

@Entity
@Table(name = "_transportation_remove")
public class TransportationRemove {
    private int id;
    private Transportation transportation;
    private ActionTime removeTime;
    private String note;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name="_transportation")
    public Transportation getTransportation() {
        return transportation;
    }
    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    @OneToOne
    @JoinColumn(name = "_remove_time")
    public ActionTime getRemoveTime() {
        return removeTime;
    }
    public void setRemoveTime(ActionTime removeTime) {
        this.removeTime = removeTime;
    }

    @Basic
    @Column(name = "_note")
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
