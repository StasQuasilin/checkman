package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.notifications.Notification;
import entity.transport.TransportUtil;
import entity.transport.Transportation;
import entity.transport.TruckInfo;
import entity.transport.Vehicle;
import org.json.simple.JSONObject;
import utils.*;
import utils.answers.SuccessAnswer;
import utils.notifications.Notificator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by szpt_user045 on 12.07.2019.
 */
@WebServlet(Branches.API.PARSE_VEHICLE)
public class ParseVehicleServletAPI extends ServletAPI {
    private final UpdateUtil updateUtil = new UpdateUtil();
    private final TruckInfoUtil infoUtil = new TruckInfoUtil();
    private final Notificator notificator = new Notificator();
    private static final String SUCCESS_V_PARSING = "notificator.vehicle.success.parsing";
    private static final String SUCCESS_VT_PARSING = "notificator.vehicle.trailer.success.parsing";
    private final LanguageBase lb = LanguageBase.getBase();
    private final VehicleParser vehicleParser = new VehicleParser();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            String key = String.valueOf(body.get(KEY));

            Vehicle vehicle = vehicleParser.parse(key);
            if (vehicle.getTrailer() != null){
                dao.save(vehicle.getTrailer());
            }

            if (!U.exist(vehicle.getModel())){
                ArrayList<TruckInfo> infos = infoUtil.getInfo(vehicle.getNumber());
                if (infos.size() > 0){
                    TruckInfo info = infos.get(0);
                    if (U.exist(info.getBrand())){
                        vehicle.setModel(info.getBrand());
                    }
                }
            }

            dao.save(vehicle);
            JSONObject json = vehicle.toJson();
            SuccessAnswer answer = new SuccessAnswer(VEHICLE, json);
            answer.add(RESULT, json);

            JSONObject object = answer.toJson();
            write(resp, object.toJSONString());
            pool.put(object);

            if (body.containsKey(Constants.TRANSPORTATION)){
                Transportation transportation = dao.getObjectById(Transportation.class, body.get(TRANSPORTATION));
                TransportUtil.setVehicle(transportation, vehicle);
                dao.save(transportation);
                updateUtil.onSave(transportation);
            }


            Worker worker = getWorker(req);

            String format = vehicle.getTrailer() != null ? SUCCESS_VT_PARSING : SUCCESS_V_PARSING;

            notificator.sendNotification(worker, new Notification(
                    String.format(
                            lb.get(worker.getLanguage(), format),
                            vehicle.getModel(), vehicle.getNumber(), vehicle.getTrailer() != null ? vehicle.getTrailer().getNumber() : EMPTY
                    )
            ).toJson());
        }
    }
}
