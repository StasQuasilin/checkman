package entity.storages;

import entity.products.Product;

import java.sql.Date;

/**
 * Created by szpt_user045 on 08.10.2019.
 */
public class StoragePoint {
    private int id;
    private Date date;
    private Storage storage;
    private Product product;
    private float amount;
}
