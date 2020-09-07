package utils.db.dao.references;

import constants.Keys;
import entity.deals.Shipper;
import entity.weight.Unit;
import utils.db.hibernate.Hibernator;

import java.util.List;

public class ReferencesDAOHibernate implements ReferencesDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public Unit getUnit(Object id) {
        return hibernator.get(Unit.class, Keys.ID, id);
    }

    @Override
    public Shipper getShipper(Object id) {
        return hibernator.get(Shipper.class, Keys.ID, id);
    }

    @Override
    public List<Shipper> getShippers() {
        return hibernator.query(Shipper.class, null);
    }

    @Override
    public List<Unit> getUnits() {
        return hibernator.query(Unit.class, null);
    }
}
