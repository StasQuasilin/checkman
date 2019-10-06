package entity.storages;

import entity.products.Product;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 15.05.2019.
 */
@Entity
@Table(name = "storage_product")
public class StorageProduct {
    private int id;
    private Storage storage;
    private Product product;
    private int max;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    @Basic
    @Column(name = "max")
    public int getMax() {
        return max;
    }
    public void setMax(int max) {
        this.max = max;
    }
}
