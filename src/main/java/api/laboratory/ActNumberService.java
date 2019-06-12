package api.laboratory;

import entity.laboratory.transportation.ActNumber;
import entity.laboratory.transportation.ActType;
import utils.boxes.IBox;

/**
 * Created by szpt_user045 on 12.06.2019.
 */
public class ActNumberService extends IBox {

    public static int getActNumber(ActType type) {
        ActNumber actNumber = hibernator.get(ActNumber.class, "type", type);
        if (actNumber == null) {
            actNumber = new ActNumber(type);
        }
        int increment = actNumber.increment();
        hibernator.save(actNumber);
        return increment;
    }
}
