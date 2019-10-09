package entity.storages;

import entity.products.Product;
import utils.storages.PointScale;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by szpt_user045 on 09.10.2019.
 */
@Entity
@Table(name="storage_period_points")
public class StoragePeriodPoint {
    private int id;
    private Date date;
    private PointScale scale;
    private Storage storage;
    private Product product;
    private float amount;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "scale")
    public PointScale getScale() {
        return scale;
    }
    public void setScale(PointScale scale) {
        this.scale = scale;
    }

    @OneToOne
    @JoinColumn(name = "storage")
    public Storage getStorage() {
        return storage;
    }
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @OneToOne
    @JoinColumn(name = "product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
