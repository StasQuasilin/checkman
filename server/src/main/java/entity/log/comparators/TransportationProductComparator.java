package entity.log.comparators;

import entity.Worker;
import entity.log.IChangeComparator;
import entity.transport.TransportationProduct;

/**
 * Created by szpt_user045 on 20.01.2020.
 */
public class TransportationProductComparator extends IChangeComparator<TransportationProduct> {
    @Override
    public void fix(TransportationProduct oldObject) {

    }

    @Override
    public void compare(TransportationProduct newObject, Worker worker) {

    }

    @Override
    public String getTitle() {
        return null;
    }
}
