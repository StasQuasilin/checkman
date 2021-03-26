package api.transport;

import api.ServletAPI;
import constants.Branches;
import entity.transport.ActionTime;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 04.11.2019.
 */
@WebServlet(Branches.API.REMOVE_TRANSPORT_TIME)
public class RemoveTransportTimeAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            if (body.containsKey(ID)){
                Transportation transportation = dao.getTransportationById(body.get(ID));
                TransportDirection direction = TransportDirection.valueOf(String.valueOf(body.get(DIRECTION)));
                ActionTime time = null;
                switch (direction){
                    case in:
                        time = transportation.getTimeIn();
                        transportation.setTimeIn(null);
                        break;
                    case out:
                        time = transportation.getTimeOut();
                        transportation.setTimeOut(null);
                        break;
                    case reg:
                        time = transportation.getTimeRegistration();
                        transportation.setTimeRegistration(null);
                        break;
                }
                if (time != null) {
                    dao.save(transportation);
                    dao.remove(time);
                    updateUtil.onSave(transportation);
                    write(resp, SUCCESS_ANSWER);
                }
            }
        }
    }
}
