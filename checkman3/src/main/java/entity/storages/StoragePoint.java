package entity.storages;

import entity.PointScale;
import entity.deals.Shipper;
import entity.references.Product;

import java.sql.Date;

public class StoragePoint {
    private int id;
    private Storage storage;
    private Date date;
    private PointScale scale;
    private Product product;
    private float amount;
    private Shipper shipper;
}
