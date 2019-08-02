package utils.hibernate;

import entity.documents.LoadPlan;
import entity.seals.Seal;
import entity.seals.SealBatch;
import entity.transport.ActionTime;
import entity.transport.Transportation;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by szpt_user045 on 01.07.2019.
 */
public class CustomHandler {
    public static void main(String[] args) {
        Hibernator hibernator = Hibernator.getInstance();
        for (LoadPlan plan : hibernator.query(LoadPlan.class, null)){
            Transportation transportation = plan.getTransportation();

            transportation.setDate(plan.getDate());
            transportation.setType(plan.getDeal().getType());
            transportation.setProduct(plan.getDeal().getProduct());
            transportation.setCounterparty(plan.getDeal().getOrganisation());
            hibernator.save(plan.getTransportation());
        }
        HibernateSessionFactory.shutdown();
    }
}
