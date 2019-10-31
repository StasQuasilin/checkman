package entity.storages;

import entity.documents.Shipper;
import entity.products.Product;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 31.10.2019.
 */
@Entity
@Table(name = "storage_entry")
public class StorageEntry {
    private int id;
    private int document;
    private StorageDocumentType type;
    private Timestamp time;
    private Storage storage;
    private Product product;
    private Shipper shipper;
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
    @Column(name = "document")
    public int getDocument() {
        return document;
    }
    public void setDocument(int document) {
        this.document = document;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public StorageDocumentType getType() {
        return type;
    }
    public void setType(StorageDocumentType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
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

    @OneToOne
    @JoinColumn(name = "shipper")
    public Shipper getShipper() {
        return shipper;
    }
    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    @Basic
    @Column(name = "amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }
}