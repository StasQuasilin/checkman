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
        quantity = oldObject.getQuantity();
        price = oldObject.getPrice();
    }

    @Override
    public void compare(DealProduct newObject, Worker worker) {
        if (productId != newObject.getProduct().getId()){
            Change change = new Change("product");
            if (productId != 0){
                changes.add(change);
            }
            change.setNewValue(newObject.getProduct().getName());

        }
        if (quantity != newObject.getQuantity()){
            Change change = new Change("quantity");
            if (quantity != 0){
                change.setOldValue(String.valueOf(quantity));
            }
            change.setNewValue(String.valueOf(newObject.getQuantity()));
        }
        if (price != newObject.getPrice()){
            Change change = new Change("price");
            if (price != 0){
                change.setOldValue(String.valueOf(price));
            }
            change.setNewValue(String.valueOf(newObject.getPrice()));
        }

        ChangeLogUtil.writeLog(newObject.getUid(), getTitle(), worker, changes);
        changes.clear();
    }

    @Override
    public String getTitle() {
        return productId == 0 ? "new" : "edit";
    }
}