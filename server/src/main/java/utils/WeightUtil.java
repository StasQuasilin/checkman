package utils;

import entity.documents.Deal;
import entity.documents.LoadPlan;
import entity.weight.Weight;
import utils.boxes.IBox;
import utils.hibernate.Hibernator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class WeightUtil {

    static dbDAO dao = dbDAOService.getDAO();

    public static void calculateDealDone(Deal deal){
        float done = 0;
        for (LoadPlan plan : dao.getPlanByDeal(deal)){
            if (plan.getTransportation().getWeight() != null && plan.getTransportation().isArchive()) {
                done += plan.getTransportation().getWeight().getNetto();
            }
        }
        if (deal.getDone() != done) {
            deal.setDone(done);
            if (done >= deal.getQuantity()){
                Archivator.add(deal);
            }
            dao.saveDeal(deal);
        }
    }
}
