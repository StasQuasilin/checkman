package entity.log.comparators;

import entity.Worker;
import entity.documents.DealProduct;
import entity.log.Change;
import entity.log.IChangeComparator;
import utils.ChangeLogUtil;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public class DealProductComparator extends IChangeComparator<DealProduct> {

    private int productId;
    private float quantity;
    private float price;

    @Override
    public void fix(DealProduct oldObject) {
        productId = oldObject.getProduct().getId();
        price = oldObject.getPrice();
    }

    @Override
    public void compare(DealProduct newObject, Worker worker) {

        ChangeLogUtil.writeLog(newObject.getUid(), getTitle(), worker, changes);
        changes.clear();
    }

    @Override
    public String getTitle() {
        return productId == 0 ? "new" : "edit";
    }
}
