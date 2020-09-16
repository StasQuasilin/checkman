package utils.db.dao.deals;

import entity.deals.Deal;
import entity.deals.DealDocument;
import entity.deals.DealProduct;
import entity.deals.DealType;
import utils.db.hibernate.Hibernator;

import java.util.HashMap;
import java.util.List;

import static constants.Keys.*;

public class DealDAOHibernate extends DealDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public List<Deal> getDealsByType(DealType type) {
        HashMap<String, Object> param = new HashMap<>();
        param.put(TYPE, type);
        param.put(ARCHIVE, false);

        return hibernator.query(Deal.class, param);
    }

    @Override
    public Deal getDealById(Object id) {
        return hibernator.get(Deal.class, ID, id);
    }

    @Override
    public void save(Deal deal) {
        hibernator.save(deal);
    }

    @Override
    public void save(DealProduct dealProduct) {
        hibernator.save(dealProduct);
    }

    @Override
    public void remove(DealProduct product) {
        hibernator.remove(product);
    }

    @Override
    public void remove(DealDocument dealDocument) {
        hibernator.remove(dealDocument);
    }

    @Override
    public void save(DealDocument dealDocument) {
        hibernator.save(dealDocument);
    }

    @Override
    public DealProduct getDealProduct(Object id) {
        return hibernator.get(DealProduct.class, ID, id);
    }
}
