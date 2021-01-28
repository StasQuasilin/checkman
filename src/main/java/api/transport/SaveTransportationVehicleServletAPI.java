package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.log.comparators.TransportComparator;
import entity.organisations.Organisation;
import entity.transport.*;
import org.json.simple.JSONObject;
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

    private final TransportComparator comparator = new TransportComparator();
    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if (body != null) {
            System.out.println(body);

            Transportation transportation = dao.getObjectById(Transportation.class, body.get(ID));
            comparator.fix(transportation);
            boolean save = false;
            if (body.containsKey(Constants.VEHICLE)){
                Vehicle vehicle = dao.getObjectById(Vehicle.class, body.get(VEHICLE));
                TransportUtil.setVehicle(transportation, vehicle);
                save = true;
            }
            if (body.containsKey(Constants.DRIVER)){
                Driver driver = dao.getObjectById(Driver.class, body.get(Constants.DRIVER));
                TransportUtil.setDriver(transportation, driver);
                save = true;
            }
            if (body.containsKey(TRAILER)){
                Trailer trailer = dao.getObjectById(Trailer.class, body.get(TRAILER));
                TransportUtil.setTrailer(transportation, trailer);
                save = true;
            }
            if (body.containsKey(TRANSPORTER)){
                Organisation transporter = dao.getObjectById(Organisation.class, body.get(TRANSPORTER));
                TransportUtil.setTransporter(transportation, transporter);
                save = true;
            }
            comparator.compare(transportation, getWorker(req));
            if (save) {
                dao.saveTransportation(transportation);
                updateUtil.onSave(transportation);
            }

            write(resp, SUCCESS_ANSWER);

            body.clear();
        } else {
            write(resp, EMPTY_BODY);
        }

    }
}
