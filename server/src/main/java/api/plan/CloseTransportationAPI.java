package api.plan;

import api.ServletAPI;
import constants.Branches;
import entity.transport.TransportUtil;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.REMOVE_PLAN)
public class CloseTransportationAPI extends ServletAPI{

    final UpdateUtil updateUtil = new UpdateUtil();
    final Logger log = Logger.getLogger(CloseTransportationAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            Transportation transportation = dao.getObjectById(Transportation.class, body.get(ID));
            if (transportation != null) {

                if (transportation.any()) {
                    TransportUtil.archive(transportation, getWorker(req));
                } else {
                    log.info("Remove transportation " + transportation.getId());
                    dao.remove(transportation);
                    updateUtil.onRemove(transportation);
                }

            }
            write(resp, SUCCESS_ANSWER);
        }
    }
}
