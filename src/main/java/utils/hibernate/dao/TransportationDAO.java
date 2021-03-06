package utils.hibernate.dao;

import entity.DealType;
import entity.documents.DealProduct;
import entity.transport.DocumentNote;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;
import utils.hibernate.DateContainers.LE;
import utils.json.JsonObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static constants.Constants.*;

public class TransportationDAO extends HibernateDAO {
    private static final String DEAL_PRODUCT_DEAL = "dealProduct/deal";
    private static final String TRANSPORTATION_DONE = "transportation/done";

    public List<Transportation> getTransportationsByDeal(Object dealId, TransportationStatus status) {
        final HashMap<String, Object> args = new HashMap<>();
        args.put(DEAL_PRODUCT_DEAL, dealId);

        if (status == TransportationStatus.unarchive) {
            args.put(TRANSPORTATION_DONE, false);
        } else if (status == TransportationStatus.archive) {
            args.put(TRANSPORTATION_DONE, true);
        }

        LinkedList<Transportation> transportations = new LinkedList<>();
        for (TransportationProduct product : hibernator.query(TransportationProduct.class, args)) {
            final Transportation transportation = product.getTransportation();
            if (!transportations.contains(transportation)) {
                transportations.add(transportation);
            }

        }

        return transportations;
    }

    public void saveProduct(TransportationProduct product) {
        hibernator.save(product);
    }

    public void saveTransportation(Transportation transportation, boolean isNew) {
        if (isNew) {
            hibernator.save(transportation.getCreateTime());
        }
        hibernator.save(transportation);
    }

    public List<Transportation> getTransportationsByType(DealType type) {
        return getTransportationsByType(type, null);
    }

    public List<Transportation> getTransportationsByType(DealType type, JsonObject jsonObject) {

        final LinkedList<Transportation> transportations = new LinkedList<>();
        final HashMap<String, Object> args = new HashMap<>();
        if (jsonObject != null && !jsonObject.isEmpty()) {
            for (Object key : jsonObject.getKeys()) {
                args.put(key.toString(), jsonObject.get(key));
            }
        } else {
            args.put(TRANPORTATION_ARCHIVE, false);
        }
        for (TransportationProduct product : hibernator.query(TransportationProduct.class, args)) {
            boolean add = false;
            final DealProduct dealProduct = product.getDealProduct();

            final Transportation transportation = product.getTransportation();
            if (dealProduct != null) {
                if (dealProduct.getDeal().getType() == type) {
                    transportations.add(transportation);
                    add = false;
                }
            } else {
                add = true;
            }
            if (add) {
                transportations.add(transportation);
            }
        }
        return transportations;
    }

    public void removeProduct(TransportationProduct product) {
        hibernator.remove(product);
    }

    public List<TransportationProduct> getTransportationsByDealProduct(Object dealProduct) {
        return hibernator.query(TransportationProduct.class, DEAL_PRODUCT, dealProduct);
    }

    public TransportationProduct getTransportationProduct(Object id) {
        return hibernator.get(TransportationProduct.class, ID, id);
    }

    public Transportation getTransportation(Object id) {
        return hibernator.get(Transportation.class, ID, id);
    }

    public List<Transportation> getDone(LE lt) {
        final HashMap<String, Object> args = new HashMap<>();
        args.put(DATE, lt);
        args.put(ARCHIVE, false);
        args.put(DONE, true);
        return hibernator.query(Transportation.class, args);
    }

    public List<TransportationProduct> getTransportations(HashMap<String, Object> params) {
        return hibernator.query(TransportationProduct.class, params);
    }

    public DocumentNote getNote(Object id) {
        return hibernator.get(DocumentNote.class, ID, id);
    }
}
