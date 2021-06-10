package utils.laboratory;

import entity.Worker;
import entity.laboratory.IAnalyses;
import entity.laboratory.SunAnalyses;
import entity.transport.ActionTime;
import entity.transport.TransportationProduct;
import utils.hibernate.DeprecatedDAO;
import utils.hibernate.dao.TransportationDAO;
import utils.json.JsonObject;

import static constants.Constants.*;

public abstract class AnalysesEditor<IAnalyses> {

    final DeprecatedDAO dao = new DeprecatedDAO();
    final TransportationDAO transportationDAO = new TransportationDAO();

    abstract boolean parse(TransportationProduct t, JsonObject jsonObject, Worker worker);
    boolean changeIt(float s1, float s2) {
        return Math.abs(s1 - s2) > 0.0001;
    }
    public boolean editAnalyses(TransportationProduct transportationProduct, JsonObject json, Worker worker) {
        if(json != null) {
            return parse(transportationProduct, json, worker);
        }

        return false;
    }

    void save(ActionTime actionTime) {
        dao.save(actionTime);
    }
}
