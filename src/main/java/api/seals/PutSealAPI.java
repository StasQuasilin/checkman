package api.seals;

import api.API;
import constants.Branches;
import entity.seals.Seal;
import entity.transport.Transportation;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 08.04.2019.
 */
@WebServlet(Branches.API.SEAL_PUT)
public class PutSealAPI extends API {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long sealId = (long) body.get("seal");
            long transportationId = (long) body.get("transportation");

            Seal seal = hibernator.get(Seal.class, "id", sealId);
            Transportation transportation = hibernator.get(Transportation.class, "id", transportationId);

            seal.setTransportation(transportation);

            hibernator.save(seal);

            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }

    }
}
