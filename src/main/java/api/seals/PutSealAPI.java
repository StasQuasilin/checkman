package api.seals;

import api.IAPI;
import constants.Branches;
import entity.answers.IAnswer;
import entity.seals.Seal;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 08.04.2019.
 */
@WebServlet(Branches.API.SEAL_PUT)
public class PutSealAPI extends IAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        long sealId = (long) body.get("seal");
        long transportationId = (long) body.get("transportation");

        Seal seal = hibernator.get(Seal.class, "id", sealId);
        Transportation transportation = hibernator.get(Transportation.class, "id", transportationId);

        seal.setTransportation(transportation);

        hibernator.save(seal);

        PostUtil.write(resp, answer);

    }
}
