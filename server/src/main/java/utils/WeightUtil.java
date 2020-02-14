package utils;

import entity.documents.Deal;
import entity.transport.Transportation;
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
        for (Transportation plan : dao.getTransportationsByDeal(deal.getId())){
            if (plan.getWeight() != null) {
                complete += plan.getWeight().getNetto();
            }
        }
        boolean save = false;
        if (deal.getQuantity() < complete){
            deal.setQuantity(Math.round(complete));
            save = true;
        }

        if (deal.getComplete() != complete) {
            deal.setComplete(complete);
            save = true;
            if (complete >= deal.getQuantity()){
                deal.setDone(true);
                Archivator.add(deal);
            } else {
                deal.setDone(false);
            }

        }
        if (save){
            dao.save(deal);

            try {
                updateUtil.onSave(deal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
