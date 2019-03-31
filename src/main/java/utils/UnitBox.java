package utils;

import api.weight.WeightAPI;
import constants.Constants;
import entity.weight.WeightUnit;
import utils.hibernate.Hibernator;

import java.util.HashMap;

/**
 * Created by quasilin on 31.03.2019.
 */
public class UnitBox {
    private static final Hibernator hibernator = Hibernator.getInstance();
    private static final HashMap<Long, WeightUnit> units = new HashMap<>();
    public static WeightUnit getUnit(long id){
        if (units.containsKey(id)){
            return units.get(id);
        } else {
            WeightUnit unit = hibernator.get(WeightUnit.class, "id", id);
            units.put(id, unit);
            return unit;
        }
    }
}
