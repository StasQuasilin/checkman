package utils.hibernate.dao;

import constants.Constants;
import entity.transport.Transportation;

import java.util.HashMap;
import java.util.List;

public class TransportationDAO extends HibernateDAO{
    public List<Transportation> getTransportationsByDeal(Object dealId, TransportationStatus status) {
        final HashMap<String, Object> args = new HashMap<>();
        args.put(Constants.DEAL, dealId);

        if (status == TransportationStatus.unarchive){
            args.put(Constants.DONE, false);
        } else if (status == TransportationStatus.archive){
            args.put(Constants.DONE, true);
        }

        return hibernator.query(Transportation.class, args);
    }
}
