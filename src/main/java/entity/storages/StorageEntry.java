package entity.storages;

import entity.JsonAble;
import entity.documents.Shipper;
import entity.products.Product;
import org.json.simple.JSONObject;
import utils.storages.IStorageStatisticUtil;
import utils.storages.PointScale;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Created by szpt_user045 on 31.10.2019.
 */
@Entity
@Table(name = "storage_entry")
public class StorageEntry extends JsonAble {

    private static final IStorageStatisticUtil ssu = new IStorageStatisticUtil() {};

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

    @Override
    public JSONObject toJson(int level) {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(DOCUMENT, document);
        object.put(DATE, time.toString());
        object.put(TYPE, type.toString());
        object.put(STORAGE, storage.toJson());
        object.put(PRODUCT, product.toJson());
        object.put(SHIPPER, shipper.toJson());
        object.put(AMOUNT, amount);
        object.put(SCALE, PointScale.detail.toString());
        PointScale scale = PointScale.detail;
        PointScale s = scale;
        LocalDate localDate = time.toLocalDateTime().toLocalDate();
        while ((s = ssu.nextScale(s)) != scale){
            scale = s;
            LocalDate beginDate = ssu.getBeginDate(localDate, scale);
            object.put(scale.toString(), Date.valueOf(beginDate).toString());
        }

        return object;
    }
}