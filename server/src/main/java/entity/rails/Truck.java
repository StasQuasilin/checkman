package entity.rails;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 30.05.2019.
 */
@Entity
@Table(name = "trucks")
public class Truck {
    private int id;
    private Train train;
    private String number;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "train")
    public Train getTrain() {
        return train;
    }
    public void setTrain(Train train) {
        this.train = train;
    }

    @Basic
    @Column(name = "number")
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        int hash = id;

        if (train != null) {
            hash = 31 * Long.hashCode(train.getId()) + hash;
        }
        if (number != null) {
            hash = 31 * number.hashCode() + hash;
        }
        return hash;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", train=" + train +
                ", number='" + number + '\'' +
                '}';
    }
}
