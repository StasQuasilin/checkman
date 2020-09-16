package utils.db.dao.transportations;

import constants.Keys;
import entity.transportations.Transportation;
import entity.transportations.TransportationDocument;
import entity.transportations.TransportationProduct;
import utils.db.hibernate.Hibernator;

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
    public List<Transportation> getTransportationsByDealProduct(Object product) {
        return hibernator.query(Transportation.class, "document/product/dealProduct", product);
    }
}
