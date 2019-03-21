package api.transport;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.answers.IAnswer;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
import utils.JsonParser;
import utils.PostUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 21.03.2019.
 */
@WebServlet(Branches.API.SAVE_TRANSPORTATION_VEHICLE)
public class SaveTransportationVehicleAPI extends IAPI {
    final String answer = JsonParser.toJson(new SuccessAnswer()).toJSONString();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);

        int transportationId = Integer.parseInt(body.get(Constants.TRANSPORTATION_ID));
        int vehicleId = Integer.parseInt(body.get(Constants.VEHICLE_ID));

        Transportation transportation = hibernator.get(Transportation.class, "id", transportationId);
        Vehicle vehicle = hibernator.get(Vehicle.class, "id", vehicleId);
        transportation.setVehicle( vehicle);
        hibernator.save(transportation);

        write(resp, answer);

        body.clear();
    }
}
