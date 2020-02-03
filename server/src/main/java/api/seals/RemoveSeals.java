package api.seals;

import api.ServletAPI;
import constants.Branches;
import entity.seals.Seal;
import org.json.simple.JSONObject;
import utils.SealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 08.04.2019.
 */
@WebServlet(Branches.API.SEAL_REMOVE)
public class RemoveSeals extends ServletAPI {

    private SealsUtil sealsUtil = new SealsUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            Object sealId = body.get("seal");

            Seal seal = dao.getSealById(sealId);
            seal.setCargo(null);
            dao.save(seal);
            sealsUtil.checkBatch(seal.getBatch());
            write(resp, SUCCESS_ANSWER);
        }

    }
}
