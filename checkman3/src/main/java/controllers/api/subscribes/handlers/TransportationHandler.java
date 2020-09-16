package controllers.api.subscribes.handlers;

import controllers.api.subscribes.Subscribes;
import entity.transportations.Transportation;
import org.json.simple.JSONArray;
import utils.db.dao.DaoService;
import utils.db.dao.transportations.TransportationDAO;

public class TransportationHandler extends Handler {

    private final TransportationDAO transportationDAO = DaoService.getTransportationDAO();

    public TransportationHandler() {
        super(Subscribes.transportations);
    }

    @Override
    protected Object getSubscribeData() {
        JSONArray array = new JSONArray();
        for (Transportation transportation : transportationDAO.getActiveTransportation()){
            array.add(transportation.toJson());
        }
        return array;
    }
}
