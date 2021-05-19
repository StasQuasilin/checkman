package utils.hibernate.dao;

import constants.Constants;
import entity.DealType;
import entity.documents.Deal;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static constants.Constants.ARCHIVE;

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
                for (TransportationProduct product : products){
                    if (product.getDealProduct().getDeal().getType() == type){
                        transportations.add(transportation);
                        break;
                    }
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
}
