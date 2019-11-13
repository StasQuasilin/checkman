package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import entity.log.comparators.TransportationComparator;
import entity.transport.*;
import org.json.simple.JSONObject;
import utils.TransportUtil;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 21.03.2019.
 */
@WebServlet(Branches.API.SAVE_TRANSPORTATION_VEHICLE)
public class SaveTransportationVehicleServletAPI extends ServletAPI {

    private final TransportationComparator comparator = new TransportationComparator();
    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if (body != null) {
            System.out.println(body);
            Transportation2 transportation = dao.getObjectById(Transportation2.class, body.get(Constants.TRANSPORTATION));
//            comparator.fix(transportation);
            long truckId = -1;

            if (body.containsKey(Constants.VEHICLE)){
                truckId = (long) body.get(Constants.VEHICLE);
            }

            if (truckId != -1){
                Truck truck = dao.getObjectById(Truck.class, truckId);
                TransportUtil.setTruck(transportation, truck);
            } else {
                TransportUtil.setTruck(transportation, null);
            }

            dao.save(transportation);
//            updateUtil.onSave(transportation);
//            comparator.compare(transportation, getWorker(req));

            write(resp, SUCCESS_ANSWER);

            body.clear();
        } else {
            write(resp, EMPTY_BODY);
        }

    }
}
