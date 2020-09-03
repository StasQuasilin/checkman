package entity.transportations;

import entity.deals.DealProduct;
import entity.rails.Carriage;
import entity.weight.Weight;

public class TransportationProduct {
    private int id;
    private TransportationDocument document;
    private Carriage carriage;
    private DealProduct dealProduct;
    private float amount;
    private Weight weight;
}
