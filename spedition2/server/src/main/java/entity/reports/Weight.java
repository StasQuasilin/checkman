package entity.reports;

import javax.persistence.*;

@Entity
@Table(name = "weights")
public class Weight {
    private int id;
    private int gross;
    private int tare;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_gross")
    public int getGross() {
        return gross;
    }
    public void setGross(int gross) {
        this.gross = gross;
    }

    @Basic
    @Column(name = "_tare")
    public int getTare() {
        return tare;
    }
    public void setTare(int tare) {
        this.tare = tare;
    }
}
