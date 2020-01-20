package entity.log.comparators;

import entity.Worker;
import entity.deal.ContractProduct;
import entity.log.IChangeComparator;

/**
 * Created by szpt_user045 on 20.01.2020.
 */
public class ContractProductComparator extends IChangeComparator<ContractProduct> {

    @Override
    public void fix(ContractProduct oldObject) {

    }

    @Override
    public void compare(ContractProduct newObject, Worker worker) {

    }

    @Override
    public String getTitle() {
        return null;
    }
}
