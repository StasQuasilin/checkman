package utils.hibernate.dao;

import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.transport.ActionTime;
import entity.transport.Transportation;
import utils.DocumentUIDGenerator;
import utils.hibernate.DateContainers.LE;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static constants.Constants.ID;

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
        final Deal deal = hibernator.get(Deal.class, ID, id);
        final Set<DealProduct> products = deal.getProducts();
        if (products.size() == 0){
            DealProduct product = new DealProduct();
            product.setDeal(deal);
            product.setProduct(deal.getProduct());
            product.setQuantity(deal.getQuantity());
            product.setUnit(deal.getUnit());
            product.setPrice(deal.getPrice());
            product.setShipper(deal.getShipper());
            product.setDone(deal.getComplete());
            product.setUid(DocumentUIDGenerator.generateUID());
            product.setCreate(deal.getCreate());
            save(product);
            products.add(product);
        }
        return deal;
    }
}
