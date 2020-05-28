package stanislav.vasilina.speditionclient.entity;

import org.json.simple.JSONObject;

import java.util.Calendar;

import static stanislav.vasilina.speditionclient.constants.Keys.ARRIVE;
import static stanislav.vasilina.speditionclient.constants.Keys.ID;
import static stanislav.vasilina.speditionclient.constants.Keys.INDEX;
import static stanislav.vasilina.speditionclient.constants.Keys.PRODUCT;

public class ReportField extends JsonAble {
    private int id;
    private int index;
    private Counterparty counterparty;
    private Calendar arriveTime;
    private Product product;
    private DealType dealType;
    private Weight weight;

    public ReportField(Calendar instance) {
        this.arriveTime = instance;
    }

    public ReportField() {

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }
    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }

    public Calendar getArriveTime() {
        return arriveTime;
    }
    public void setArriveTime(Calendar arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public DealType getDealType() {
        return dealType;
    }
    public void setDealType(DealType dealType) {
        this.dealType = dealType;
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
        jsonObject.put(ID, id);
        jsonObject.put(INDEX, index);
        jsonObject.put(ARRIVE, arriveTime.getTimeInMillis());
        if (product != null) {
            jsonObject.put(PRODUCT, product.toJson());
        }
        return jsonObject;
    }
}
