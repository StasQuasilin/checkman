package api.deal;

import entity.DealType;
import entity.Worker;
import entity.documents.Deal;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.products.ProductAction;
import org.json.simple.JSONObject;

import java.sql.Date;
import java.util.List;

public class FastDealEditor extends DealEditor{


    public Deal editDeal(JSONObject json, Worker worker) {
        boolean isNew = false;
        Deal deal = dao.getObjectById(Deal.class, json.get(ID));

        if (deal == null){
            deal = new Deal();
            Product product = dao.getObjectById(Product.class, json.get(PRODUCT));
            List<ProductAction> actions = dao.getProductActionsByProduct(product);
            DealType type = null;
            if (actions.size() == 1){
                type = actions.get(0).getType();
            } else {
                boolean founded = false;
                for (ProductAction action : actions){
                    if (!action.getEditable()){
                        type = action.getType();
                        founded = true;
                    }
                }
                if(!founded){
                    type = actions.get(0).getType();
                }
            }
            Date date = Date.valueOf(String.valueOf(json.get(DATE)));
            deal.setDate(date);
            deal.setDateTo(date);
            deal.setType(type);
            deal.setProduct(product);
            deal.setUnit(product.getUnit());
            deal.setOrganisation(dao.getObjectById(Organisation.class, json.get(COUNTERPARTY)));
            deal.setShipper(dao.getShipperList().get(0));

            isNew = true;

        }

        if(isNew){
            newDeal(deal, worker);
            dao.save(deal);
        }

        return deal;
    }
}
