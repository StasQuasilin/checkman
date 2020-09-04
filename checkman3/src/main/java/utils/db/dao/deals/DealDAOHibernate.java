package utils.db.dao.deals;

import entity.deals.Deal;
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
}
