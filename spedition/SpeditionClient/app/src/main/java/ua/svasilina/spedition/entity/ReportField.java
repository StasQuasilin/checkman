package ua.svasilina.spedition.entity;

import org.json.simple.JSONObject;

import java.util.Calendar;

import static ua.svasilina.spedition.constants.Keys.ARRIVE;
import static ua.svasilina.spedition.constants.Keys.COUNTERPARTY;
import static ua.svasilina.spedition.constants.Keys.ID;
import static ua.svasilina.spedition.constants.Keys.LEAVE;
import static ua.svasilina.spedition.constants.Keys.MONEY;
import static ua.svasilina.spedition.constants.Keys.WEIGHT;

public class ReportField extends JsonAble implements Comparable<ReportField> {
    private String uuid;
    private String counterparty;
    private Calendar arriveTime;
    private Calendar leaveTime;
    private int money;
    private Weight weight;

    public ReportField() {}

    public ReportField(Calendar arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCounterparty() {
        return counterparty;
    }
    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }

    public Calendar getArriveTime() {
        return arriveTime;
    }
    public void setArriveTime(Calendar arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Calendar getLeaveTime() {
        return leaveTime;
    }
    public void setLeaveTime(Calendar leaveTime) {
        this.leaveTime = leaveTime;
    }

    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }

    public Weight getWeight() {
        return weight;
    }
    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, uuid);
        jsonObject.put(COUNTERPARTY, counterparty);
        if (arriveTime != null) {
            jsonObject.put(ARRIVE, arriveTime.getTimeInMillis());
        }
        if (leaveTime != null){
            jsonObject.put(LEAVE, leaveTime.getTimeInMillis());
        }
        jsonObject.put(MONEY, money);
        if (weight != null){
            jsonObject.put(WEIGHT, weight.toJson());
        }

        return jsonObject;
    }


    @Override
    public int compareTo(ReportField o) {
        return arriveTime.compareTo(o.arriveTime);
    }
}
