package entity.deals;

import entity.references.Product;

public class DealProduct {
    private int id;
    private DealType type;
    private DealDocument document;
    private Product product;
    private float amount;
    private float price;
    private Shipper shipper;
}
