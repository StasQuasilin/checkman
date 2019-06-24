package api.seals;

import api.API;
import constants.Branches;
import entity.seals.Seal;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

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

    dbDAO dao = dbDAOService.getDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {

            Seal seal = dao.getSealById(body.get("seal"));
            Transportation transportation = dao.getTransportationById(body.get("transportation"));
            if (seal != null) {
                seal.setTransportation(transportation);
                dao.save(seal);
            }

            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }

    }
}
