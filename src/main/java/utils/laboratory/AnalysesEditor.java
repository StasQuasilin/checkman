package utils.laboratory;

import entity.Worker;
import entity.transport.ActionTime;
import entity.transport.TransportationProduct;
import utils.hibernate.DeprecatedDAO;
import utils.hibernate.dao.TransportationDAO;
import utils.json.JsonObject;

public abstract class AnalysesEditor {

    final DeprecatedDAO dao = new DeprecatedDAO();
    final TransportationDAO transportationDAO = new TransportationDAO();

    public abstract boolean editAnalyses(TransportationProduct transportationProduct, JsonObject json, Worker worker);
    void save(ActionTime actionTime) {
        dao.save(actionTime);
    }
}
