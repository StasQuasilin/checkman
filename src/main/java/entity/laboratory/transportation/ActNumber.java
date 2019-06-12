package entity.laboratory.transportation;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 12.06.2019.
 */
@Entity
@Table(name = "act_numbers")
public class ActNumber {
    private int id;
    private int number;
    private ActType type;

    public ActNumber() {}
    public ActNumber(ActType type) {
        this.type = type;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "number")
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    public ActType getType() {
        return type;
    }
    public void setType(ActType type) {
        this.type = type;
    }

    @Transient
    public int increment() {
        return ++number;
    }
}
