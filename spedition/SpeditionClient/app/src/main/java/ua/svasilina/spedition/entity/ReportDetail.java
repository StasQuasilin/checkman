package ua.svasilina.spedition.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.simple.JSONObject;

import ua.svasilina.spedition.constants.Keys;

public class ReportDetail implements JsonAble{
    private long id = -1;
    private int serverId;
    private String uuid;
    private Driver driver;
    private Product product;
    private Weight ownWeight;
    private Weight weight;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public int getServerId() {
        return serverId;
    }
    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Driver getDriver() {
        return driver;
    }
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public Weight getOwnWeight() {
        return ownWeight;
    }
    public void setOwnWeight(Weight ownWeight) {
        this.ownWeight = ownWeight;
    }

    public Weight getWeight() {
        return weight;
    }
    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj.getClass().equals(getClass()) && obj.hashCode() == hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return (driver != null ? driver.toString() : Keys.EMPTY) +
                (driver != null && ownWeight != null ? Keys.COLON+ Keys.SPACE : Keys.EMPTY) +
                (ownWeight != null ? ownWeight.toString() : Keys.EMPTY);
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Keys.ID, id);
        jsonObject.put(Keys.UUID, uuid);
        if (driver != null) {
            jsonObject.put(Keys.DRIVER, driver.toJson());
        }
        if(weight != null) {
            jsonObject.put(Keys.WEIGHT, weight.toJson());
        }
        if(product != null){
            jsonObject.put(Keys.PRODUCT, product.toJson());
        }
        return jsonObject;
    }
}
