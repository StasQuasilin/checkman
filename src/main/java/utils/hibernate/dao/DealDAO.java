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
        if (deal != null) {
            final Set<DealProduct> products = deal.getProducts();
            if (products.size() == 0) {
                DealProduct product = initDealProduct(deal);
                save(product);
                products.add(product);
            }
        }
        return deal;
    }

    public DealProduct getDealProduct(Object id) {
        DealProduct product = hibernator.get(DealProduct.class, ID, id);
        if (product == null){
            final Deal deal = getDealById(id);
            if (deal != null){
                product = initDealProduct(deal);
            }
        }
        return product;
    }

    private DealProduct initDealProduct(Deal deal) {
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
        return product;
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
}
