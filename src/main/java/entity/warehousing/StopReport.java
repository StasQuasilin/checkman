package entity.warehousing;

import entity.Subdivision;
import entity.production.Turn;
import entity.transport.ActionTime;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * Created by szpt_user045 on 10.12.2019.
 */
@Entity
@Table(name = "stop_reports")
public class StopReport {
    private int id;
    private Turn turn;
    private Subdivision subdivision;
    private String reason;
    private int days;
    private int hours;
    private int minutes;
    private ActionTime createTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "turn")
    public Turn getTurn() {
        return turn;
    }
    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    @OneToOne
    @JoinColumn(name = "subdivision")
    public Subdivision getSubdivision() {
        return subdivision;
    }
    public void setSubdivision(Subdivision subdivision) {
        this.subdivision = subdivision;
    }

    @Basic
    @Column(name = "reason")
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    @Basic
    @Column(name = "days")
    public int getDays() {
        return days;
    }
    public void setDays(int days) {
        this.days = days;
    }

    @Basic
    @Column(name = "hours")
    public int getHours() {
        return hours;
    }
    public void setHours(int hours) {
        this.hours = hours;
    }

    @Basic
    @Column(name = "minutes")
    public int getMinutes() {
        return minutes;
    }
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }
}
