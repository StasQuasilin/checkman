package entity.transport;

import entity.DealType;
import entity.documents.Shipper;
import entity.products.Product;
import entity.storages.Storage;
import entity.storages.StorageDocument;
import entity.storages.StorageDocumentType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Kvasik on 14.10.2019.
 */
@Entity
@Table(name = "transport_storage")
public class TransportStorageUsed extends StorageDocument {
    private int id;
    private Transportation transportation;
    private Shipper shipper;
    private Storage storage;
    private float amount;
    private ActionTime create;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "transportation")
    public Transportation getTransportation() {
        return transportation;
    }
    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    @OneToOne
    @JoinColumn(name = "shipper")
    public Shipper getShipper() {
        if (shipper == null) {
            return transportation.getShipper();
        }
        return shipper;
    }
    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    @OneToOne
    @JoinColumn(name = "storage")
    public Storage getStorage() {
        return storage;
    }

    @Transient
    @Override
    public Product getProduct() {
        return transportation.getProduct();
    }
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Transient
    @Override
    public StorageDocumentType getType() {
        return StorageDocumentType.weight;
    }

    @Transient
    @Override
    public Timestamp getDate() {
//        Timestamp bruttoTime = transportation.getWeight().getBruttoTime().getTime();
//        Timestamp taraTime = transportation.getWeight().getTaraTime().getTime();
//        return bruttoTime.after(taraTime) ? bruttoTime : taraTime;
        return Timestamp.valueOf(LocalDateTime.of(transportation.getDate().toLocalDate(), LocalTime.now()));
    }

    @Transient
    @Override
    public float getQuantity() {
        return transportation.getType() == DealType.buy ? getAmount() : -getAmount();
    }

    @Basic
    @Column(name = "amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @OneToOne
    @JoinColumn(name = "created")
    public ActionTime getCreate() {
        return create;
    }
    public void setCreate(ActionTime create) {
        this.create = create;
    }
}
