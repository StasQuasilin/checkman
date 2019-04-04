package entity.laboratory.subdivisions.extraction;

import entity.Worker;
import entity.transport.ActionTime;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by quasilin on 30.03.2019.
 */
@Entity
@Table(name = "extraction_crude")
public class ExtractionCrude {
    private int id;
    private ExtractionTurn turn;
    private Timestamp time;
    private float humidityIncome;
    private float fraction;
    private float miscellas;
    private float humidity;
    private float dissolvent;
    private float grease;
    private ActionTime createTime;
    private Worker worker;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "turn")
    public ExtractionTurn getTurn() {
        return turn;
    }
    public void setTurn(ExtractionTurn turn) {
        this.turn = turn;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "humidity_income")
    public float getHumidityIncome() {
        return humidityIncome;
    }
    public void setHumidityIncome(float humidityIncome) {
        this.humidityIncome = humidityIncome;
    }

    @Basic
    @Column(name = "fraction")
    public float getFraction() {
        return fraction;
    }
    public void setFraction(float fraction) {
        this.fraction = fraction;
    }

    @Basic
    @Column(name = "miscellas")
    public float getMiscellas() {
        return miscellas;
    }
    public void setMiscellas(float miscellas) {
        this.miscellas = miscellas;
    }

    @Basic
    @Column(name = "humidity")
    public float getHumidity() {
        return humidity;
    }
    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    @Basic
    @Column(name = "dissolvent")
    public float getDissolvent() {
        return dissolvent;
    }
    public void setDissolvent(float dissolvent) {
        this.dissolvent = dissolvent;
    }

    @Basic
    @Column(name = "grease")
    public float getGrease() {
        return grease;
    }
    public void setGrease(float grease) {
        this.grease = grease;
    }

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }

    @OneToOne
    @JoinColumn(name = "creator")
    public Worker getWorker() {
        return worker;
    }
    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
