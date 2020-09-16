package utils.db.dao.transportations;

import constants.Keys;
import entity.transportations.Transportation;
import entity.transportations.TransportationDocument;
import entity.transportations.TransportationProduct;
import utils.db.hibernate.Hibernator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TransportationDAOHibernate implements TransportationDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public Transportation getTransportationById(Object id) {
        return hibernator.get(Transportation.class, Keys.ID, id);
    }

    @Override
    public void save(Transportation transportation) {
        hibernator.save(transportation);
    }

    @Override
    public void save(TransportationDocument document) {
        hibernator.save(document);
    }

    @Override
    public void save(TransportationProduct product) {
        hibernator.save(product);
    }

    @Override
    public List<Transportation> getTransportationsByDealProduct(Object dp) {
        final HashMap<Integer, Transportation> transportationHashMap = new HashMap<>();
        for (TransportationProduct product : hibernator.query(TransportationProduct.class, "dealProduct", dp)){
            final Transportation transportation = product.getDocument().getTransportation();
            final int id = transportation.getId();
            if (!transportationHashMap.containsKey(id)){
                transportationHashMap.put(id, transportation);
            }
        }
        return new LinkedList<>(transportationHashMap.values());
    }

    @Override
    public List<Transportation> getActiveTransportation() {
        return hibernator.query(Transportation.class, "archive", false);
    }
}
