package entity.storages;

import entity.products.Product;
import entity.storages.Storage;
import utils.storages.DocumentType;
import utils.storages.PointScale;

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
    private DocumentType documentType;
    private int document;
    private String comment;
}
