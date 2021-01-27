package utils;

import entity.documents.Deal;
import entity.transport.Transportation;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.io.IOException;
import java.sql.Date;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class WeightUtil {

    static dbDAO dao = dbDAOService.getDAO();
    private static final UpdateUtil updateUtil = new UpdateUtil();

    public static void calculateDealDone(Deal deal){
        float complete = 0;
        Date dateTo = deal.getDateTo();
        for (Transportation transportation : dao.getTransportationsByDeal(deal.getId())){
            if (transportation.getWeight() != null) {
                complete += transportation.getWeight().getNetto();
            }

            if (dateTo == null || dateTo.before(transportation.getDate())){
                dateTo = transportation.getDate();
            }
        }

        boolean save = false;

        if (deal.getDateTo() == null || !deal.getDateTo().equals(dateTo)){
            deal.setDateTo(dateTo);
        }

        if (deal.getQuantity() < complete){
            deal.setQuantity(Math.round(complete));
            save = true;
        }

        if (deal.getComplete() != complete) {
            deal.setComplete(complete);
            save = true;
            deal.setDone(complete >= deal.getQuantity());
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
