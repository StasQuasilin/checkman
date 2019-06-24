package utils;

import constants.Branches;
import entity.documents.Deal;
import utils.hibernate.Hibernator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 15.03.2019.
 */
public class DealBox {

    private final HashMap<Integer, Deal> deals = new HashMap<>();
    final static HashMap<String, Object> parameters = new HashMap<>();

    {
        parameters.put("archive", false);
    }
    private static final DealBox BOX = new DealBox();

    public static DealBox getBOX() {
        return BOX;
    }


    private DealBox() {

    }

    public void update(Deal deal) {
        deals.put(deal.getId(), deal);
    }

    public HashMap<Integer, Deal> getDeals(){
        return deals;
    }


}
