package utils;

import entity.documents.Deal;
import entity.documents.LoadPlan;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.io.IOException;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class WeightUtil {

    static dbDAO dao = dbDAOService.getDAO();
    private static final UpdateUtil updateUtil = new UpdateUtil();

    public static void calculateDealDone(Deal deal){
        float complete = 0;
        for (LoadPlan plan : dao.getPlanByDeal(deal)){
            if (plan.getTransportation().getWeight() != null) {
                complete += plan.getTransportation().getWeight().getNetto();
            }
        }
        if (deal.getComplete() != complete) {
            deal.setComplete(complete);
            try {
                updateUtil.onSave(deal);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (complete >= deal.getQuantity()){
                deal.setDone(true);
                Archivator.add(deal);
            } else {
                deal.setDone(false);
            }
            dao.saveDeal(deal);
        }
    }
}
