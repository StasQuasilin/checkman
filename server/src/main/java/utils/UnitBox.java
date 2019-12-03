package utils;

import entity.weight.Unit;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.util.HashMap;

/**
 * Created by quasilin on 31.03.2019.
 */
public class UnitBox {

    private static final HashMap<Long, Unit> units = new HashMap<>();
    static dbDAO dao = dbDAOService.getDAO();

    public static Unit getUnit(long id){
        if (units.containsKey(id)){
            return units.get(id);
        } else {
            Unit unit = dao.getWeightUnitById(id);
            units.put(id, unit);
            return unit;
        }
    }
}
