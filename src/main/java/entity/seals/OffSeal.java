package entity.seals;

import constants.Constants;
import entity.transport.ActionTime;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 20.02.2020.
 */
@Entity
@Table(name = Constants.OFF_SEALS)
public class OffSeal implements Constants{

    private int id;
    private String number;
    private String reason;
    private ActionTime offTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = NUMBER)
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = REASON)
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    @OneToOne
    @JoinColumn(name = "off_time")
    public ActionTime getOffTime() {
        return offTime;
    }
    public void setOffTime(ActionTime offTime) {
        this.offTime = offTime;
    }
}
