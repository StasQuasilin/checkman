package entity;

import javax.persistence.*;

@Entity
@Table(name = "weights")
public class Weight {
    private int id;
    private float gross;
    private float tare;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "gross")
    public float getGross() {
        return gross;
    }
    public void setGross(float gross) {
        this.gross = gross;
    }

    @Basic
    @Column(name = "tare")
    public float getTare() {
        return tare;
    }
    public void setTare(float tare) {
        this.tare = tare;
    }
}
