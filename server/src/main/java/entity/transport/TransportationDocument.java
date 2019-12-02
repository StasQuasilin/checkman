package entity.transport;

import entity.JsonAble;
import entity.organisations.LoadAddress;
import org.json.simple.JSONObject;

import java.util.Set;

/**
 * Created by szpt_user045 on 29.11.2019.
 */
public class TransportationDocument extends JsonAble{
    private int id;
    private Transportation2 transportation;
    private LoadAddress address;
    private Set<TransportationProduct> products;

    @Override
    public JSONObject toJson() {
        return null;
    }
}
