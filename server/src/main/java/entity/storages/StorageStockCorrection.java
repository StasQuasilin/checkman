package entity.storages;

import entity.documents.Shipper;
import entity.products.Product;
import entity.transport.ActionTime;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by szpt_user045 on 03.01.2020.
 */
@Entity
@Table(name = "stock_corrections")
public class StorageStockCorrection extends StorageDocument {
    private int id;
    private StorageStockCorrectionDocument document;
    private Shipper shipper;
    private Storage storage;
    private Product product;
    private float correction;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "correction_document")
    public StorageStockCorrectionDocument getDocument() {
        return document;
    }
    public void setDocument(StorageStockCorrectionDocument document) {
        this.document = document;
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

    @Override
    @Transient
    public StorageDocumentType getType() {
        return StorageDocumentType.correction;
    }

    @Override
    @Transient
    public Timestamp getDate() {
        return Timestamp.valueOf(LocalDateTime.of(document.getDate().toLocalDate(), LocalTime.now()));
    }

    @Override
    @Transient
    public float getQuantity() {
        return correction;
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
    @Column(name = "correction")
    public float getCorrection() {
        return correction;
    }
    public void setCorrection(float correction) {
        this.correction = correction;
    }
}
