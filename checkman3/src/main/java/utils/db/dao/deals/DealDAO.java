package utils.db.dao.deals;

import entity.deals.Deal;
import entity.deals.DealDocument;
import entity.deals.DealProduct;
import entity.deals.DealType;

import java.util.List;

public abstract class DealDAO {
    public abstract List<Deal> getDealsByType(DealType type);

    public abstract Deal getDealById(Object id);

    public abstract void save(Deal deal);

    public abstract void save(DealProduct dealProduct);

    public abstract void remove(DealProduct product);

    public abstract void remove(DealDocument dealDocument);

    public abstract void save(DealDocument dealDocument);

    public abstract DealProduct getDealProduct(Object id);
}
