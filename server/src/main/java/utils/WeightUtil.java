package utils;

import entity.deal.ContractProduct;
import entity.documents.Deal;
import entity.documents.LoadPlan;
import entity.transport.TransportationProduct;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.io.IOException;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class WeightUtil {

    static dbDAO dao = dbDAOService.getDAO();
    private static final UpdateUtil updateUtil = new UpdateUtil();

    public static void calculateDealDone(ContractProduct contractProduct){
        float complete = 0;
        for (TransportationProduct plan : dao.getPlanByDeal(contractProduct)){
            if (plan.getWeight() != null) {
                complete += plan.getWeight().getNetto();
            }
        }
        boolean save = false;
        if (contractProduct.getAmount() < complete){
            contractProduct.setAmount(1f * Math.round(complete * 100) / 100);
            save = true;
        }

        if (contractProduct.getDone() != complete) {
            contractProduct.setDone(complete);
            save = true;
//            if (complete >= deal.getAmount()){
//                deal.setDone(true);
//                Archivator.add(deal);
//            } else {
//                deal.setDone(false);
//            }
        }
        if (save){
            dao.save(contractProduct);

//            try {
//                updateUtil.onSave(deal);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
}
