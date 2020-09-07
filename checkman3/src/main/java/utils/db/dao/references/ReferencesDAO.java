package utils.db.dao.references;

import entity.deals.Shipper;
import entity.weight.Unit;

import java.util.List;

public interface ReferencesDAO {
    Unit getUnit(Object id);

    Shipper getShipper(Object id);

    List<Shipper> getShippers();

    List<Unit> getUnits();
}
