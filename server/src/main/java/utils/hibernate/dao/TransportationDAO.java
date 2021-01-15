package utils.hibernate.dao;

import constants.Constants;
import entity.transport.Transportation;

import java.util.HashMap;
import java.util.List;

public class TransportationDAO extends HibernateDAO{
    public List<Transportation> getTransportationsByDeal(Object dealId, boolean activeOnly) {
        final HashMap<String, Object> args = new HashMap<>();
        args.put(Constants.DEAL, dealId);
        if (activeOnly){
            args.put(Constants.DONE, false);
        }

        return hibernator.query(Transportation.class, args);
    }
}
