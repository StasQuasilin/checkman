package ua.svasilina.spedition.entity;

import androidx.annotation.Nullable;

public class ReportDetail {
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
}
