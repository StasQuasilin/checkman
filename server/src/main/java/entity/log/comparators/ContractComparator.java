package entity.log.comparators;

import entity.Worker;
import entity.deal.Contract;
import entity.log.IChangeComparator;

/**
 * Created by szpt_user045 on 20.01.2020.
 */
public class ContractComparator extends IChangeComparator<Contract> {
    @Override
    public void fix(Contract oldObject) {

    }

    @Override
    public void compare(Contract newObject, Worker worker) {

    }

    @Override
    public String getTitle() {
        return null;
    }
}
