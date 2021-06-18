package utils.hibernate.dao;

import constants.Constants;
import entity.DealType;
import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static constants.Constants.*;

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

    public void saveProduct(TransportationProduct product) {
        hibernator.save(product);
    }

    public void saveTransportation(Transportation transportation, boolean isNew) {
        if(isNew) {
            hibernator.save(transportation.getCreateTime());
        }
        hibernator.save(transportation);
    }

    public List<Transportation> getTransportationsByType(DealType type) {

        final LinkedList<Transportation> transportations = new LinkedList<>();
        for (Transportation transportation : hibernator.query(Transportation.class, ARCHIVE, false)){
            final Set<TransportationProduct> products = transportation.getProducts();
            if (products.size() > 0){
                boolean add = false;
                for (TransportationProduct product : products){
                    final DealProduct dealProduct = product.getDealProduct();
                    if (dealProduct != null){
                        if (dealProduct.getDeal().getType() == type){
                            transportations.add(transportation);
                            add = false;
                            break;
                        }
                    } else {
                        add = true;
                    }
                }
                if (add){
                    transportations.add(transportation);
                }
            } else {
                final Deal deal = transportation.getDeal();
                if (deal != null && deal.getType() == type){
                    transportations.add(transportation);
                }
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
}
