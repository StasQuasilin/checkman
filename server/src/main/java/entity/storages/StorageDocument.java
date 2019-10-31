package entity.storages;

import entity.documents.Shipper;
import entity.products.Product;

import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 31.10.2019.
 */
public abstract class StorageDocument {
    public abstract StorageDocumentType getType();
    public abstract Timestamp getDate();
    public abstract float getQuantity();
    public abstract Storage getStorage();
    public abstract Product getProduct();
    public abstract Shipper getShipper();
    public abstract int getId();

}
