package utils.hibernate.dao;

import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;
import utils.DocumentUIDGenerator;
import utils.hibernate.DateContainers.LE;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static constants.Constants.*;

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

    public Deal getDealById(Object id) {
        return hibernator.get(Deal.class, ID, id);
    }

    public DealProduct getDealProduct(Object id) {
        return hibernator.get(DealProduct.class, ID, id);
    }

    public void saveDealProduct(DealProduct dealProduct) {
        save(dealProduct);
    }

    public boolean removeDealProduct(DealProduct product) {
        final List<TransportationProduct> transportations = transportationDAO.getTransportationsByDealProduct(product);

        if (transportations.size() == 0){
            remove(product);
            return true;
        }
        return false;
    }

    public List<Deal> getDealsByOrganisation(Object organisationId) {
        final HashMap<String, Object> parameters = hibernator.getParams();
        parameters.put(ARCHIVE, false);
        parameters.put(ORGANISATION, organisationId);
        return hibernator.query(Deal.class, parameters);
    }
}
