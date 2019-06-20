package entity.documents;

import entity.products.Product;
import entity.Worker;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "deal_products")
public class DealProduct {
    private int id;
    private Deal deal;
    private Product product;
    private float quantity = 0;
    private float done = 0;
    private float price = 0;
    private Worker creator;
    private String uid;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "deal")
    public Deal getDeal() {
        return deal;
    }
    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    @OneToOne
    @JoinColumn(name = "product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @Basic
    @Column(name = "quantity")
    public float getQuantity() {
        return quantity;
    }
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "done")
    public float getDone() {
        return done;
    }
    public void setDone(float done) {
        this.done = done;
    }

    @Basic
    @Column(name = "price")
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    @OneToOne
    @JoinColumn(name = "creator")
    public Worker getCreator() {
        return creator;
    }
    public void setCreator(Worker creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "uid")
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * product.getId() + hash;
        hash = 31 * Float.hashCode(quantity) + hash;
        hash = 31 * Float.hashCode(price) + hash;
        return hash;
    }
}
