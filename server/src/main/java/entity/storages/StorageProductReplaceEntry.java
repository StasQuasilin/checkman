package entity.storages;

import entity.documents.Shipper;
import entity.products.Product;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 06.11.2019.
 */
@Entity
@Table(name = "storage_product_replace_entry")
public class StorageProductReplaceEntry extends StorageDocument{
    private int id;
    private StorageProductReplace replace;
    private Storage storage;
    private Product product;
    private Shipper shipper;
    private float amount;

    @Override
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "replace")
    public StorageProductReplace getReplace() {
        return replace;
    }
    public void setReplace(StorageProductReplace replace) {
        this.replace = replace;
    }

    @Override
    @OneToOne
    @JoinColumn(name = "storage")
    public Storage getStorage() {
        return storage;
    }
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    @OneToOne
    @JoinColumn(name = "product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
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

    @Override
    @Transient
    public StorageDocumentType getType() {
        return StorageDocumentType.displacement;
    }

    @Override
    @Transient
    public Timestamp getDate() {
        return replace.getTime();
    }

    @Override
    @Transient
    public float getQuantity() {
        return amount;
    }
}
