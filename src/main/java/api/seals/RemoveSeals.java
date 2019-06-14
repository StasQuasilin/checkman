package api.seals;

import api.API;
import constants.Branches;
import entity.seals.Seal;
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
@WebServlet(Branches.API.SEAL_REMOVE)
public class RemoveSeals extends API {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        long sealId = (long) body.get("seal");
        Seal seal = hibernator.get(Seal.class, "id", sealId);
        seal.setTransportation(null);
        hibernator.save(seal);
        write(resp, answer);
    }
}
