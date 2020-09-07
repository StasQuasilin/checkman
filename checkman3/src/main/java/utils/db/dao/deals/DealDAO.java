package utils.db.dao.deals;

import entity.deals.Deal;
import entity.deals.DealType;

import java.util.List;

public abstract class DealDAO {
    public abstract List<Deal> getDealsByType(DealType type);

    public abstract Deal getDealById(Object id);

    public abstract void save(Deal deal);
}
