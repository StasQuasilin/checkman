package entity.warehousing;

import entity.deals.Shipper;
import entity.references.Product;
import entity.storages.Storage;

import java.sql.Date;

public class StorageTransfer {
    private int id;
    private Date date;
    private Storage from;
    private Storage to;
    private Product product;
    private float amount;
    private Shipper shipper;
}
