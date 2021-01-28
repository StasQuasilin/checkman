package utils.boxes;

import entity.documents.Deal;

/**
 * Created by szpt_user045 on 24.06.2019.
 */
public class DealBox extends IBox<Deal> {

    private static final DealBox box = new DealBox();

    public static DealBox getBox() {
        return box;
    }


    @Override
    public void init() {
    }

    @Override
    public void save(Deal deal) {
        put(deal);
    }

    @Override
    public void put(Deal deal) {
        hashMap.put(deal.getId(), deal);
    }

    @Override
    public Deal get(int id) {
        if (hashMap.containsKey(id)){
            return hashMap.get(id);
        }
        return null;
    }

    @Override
    public Deal remove(int id) {
        if (hashMap.containsKey(id)){
            return hashMap.remove(id);
        }
        return null;
    }
}
