package controllers.api.subscribes.handlers;

import controllers.api.subscribes.Subscribes;
import entity.deals.Deal;
import entity.deals.DealType;
import org.json.simple.JSONArray;
import utils.db.dao.DaoService;
import utils.db.dao.deals.DealDAO;

public class DealHandler extends Handler {

    private final DealDAO dealDAO = DaoService.getDealDAO();
    private final DealType dealType;
    public DealHandler(Subscribes subscribes, DealType type) {
        super(subscribes);
        dealType = type;
    }

    @Override
    protected Object getSubscribeData() {
        JSONArray array = new JSONArray();
        for (Deal deal : dealDAO.getDealsByType(dealType)){
            array.add(deal.toJson());
        }
        return array;
    }
}
