package utils;

import entity.documents.Deal;
import entity.documents.LoadPlan;
import entity.weight.Weight;
import utils.boxes.IBox;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class WeightUtil extends IBox{
    public static void calculateDealDone(Deal deal){
        float done = 0;
        for (LoadPlan plan : HIBERNATOR.query(LoadPlan.class, "deal", deal)){
            if (plan.getTransportation().isArchive()) {
                for (Weight weight : plan.getTransportation().getWeights()) {
                    done += weight.getNetto();
                }
            }
        }
        if (deal.getDone() != done) {
            deal.setDone(done);
            deal.setArchive(done >= deal.getQuantity());
            HIBERNATOR.save(deal);
        }
    }
}
