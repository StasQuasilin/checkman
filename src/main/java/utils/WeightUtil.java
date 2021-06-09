package utils;

import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;
import entity.weight.Weight;
import utils.hibernate.dao.DealDAO;
import utils.hibernate.dao.TransportationDAO;
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
    private static final TransportationDAO transportationDAO = new TransportationDAO();
    private static final DealDAO dealDao = new DealDAO();

    public static void calculateDealDone(DealProduct dealProduct){
        System.out.println(dealProduct.getId());
        float done = 0;
        for (TransportationProduct product : transportationDAO.getTransportationsByDealProduct(dealProduct)){
            final Weight weight = product.getWeight();
            if (weight != null){
                done += weight.getNetto();
                System.out.println(done);
            }
        }
        System.out.println(Math.abs(dealProduct.getDone() - done));
        if (Math.abs(dealProduct.getDone() - done) > 0.001){
            dealProduct.setDone(done);
            dealDao.saveDealProduct(dealProduct);
            if (dealProduct.isOver()){
                final Deal deal = dealProduct.getDeal();
                updateUtil.onSave(deal);
            }
        }
    }
}
