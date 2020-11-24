package ua.svasilina.spedition.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.simple.JSONObject;

import ua.svasilina.spedition.constants.Keys;

public class ReportDetail implements JsonAble{
    private int id = -2;
    private int serverId;
    private Driver driver;
    private Product product;
    private Weight ownWeight;
    private Weight weight;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
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
        return id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj.getClass().equals(getClass()) && obj.hashCode() == hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        if (driver != null){
            return driver.toString();
        }
        return Keys.EMPTY;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Keys.ID, id);
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
