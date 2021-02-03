package utils.hibernate.dao;

import entity.documents.Deal;
import entity.transport.Transportation;
import utils.hibernate.DateContainers.LE;

import java.util.HashMap;
import java.util.List;

public class DealDAO extends HibernateDAO{

    private final TransportationDAO transportationDAO = new TransportationDAO();

    public List<Deal> getDealsAfter(LE le) {
        final HashMap<String, Object> args = new HashMap<>();
        args.put("dateTo", le);
        args.put("archive", false);
        final List<Deal> deals = hibernator.query(Deal.class, args);
        for (int i = 0; i < deals.size(); ){
            final Deal deal = deals.get(i);
            final List<Transportation> t = transportationDAO.getTransportationsByDeal(deal, TransportationStatus.unarchive);
            if (t.size() > 0){
                deals.remove(i);
            } else {
                i++;
            }
        }
        return deals;
    }
}
