package stanislav.vasilina.speditionclient.entity;

import org.json.simple.JSONObject;

import java.util.Calendar;

import static stanislav.vasilina.speditionclient.constants.Keys.ARRIVE;
import static stanislav.vasilina.speditionclient.constants.Keys.COUNTERPARTY;
import static stanislav.vasilina.speditionclient.constants.Keys.ID;
import static stanislav.vasilina.speditionclient.constants.Keys.MONEY;
import static stanislav.vasilina.speditionclient.constants.Keys.WEIGHT;

public class ReportField extends JsonAble implements Comparable<ReportField> {
    private String uuid;
    private String counterparty;
    private Calendar arriveTime;

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
        jsonObject.put(ARRIVE, arriveTime.getTimeInMillis());
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
