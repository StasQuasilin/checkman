package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.answers.IAnswer;
import entity.log.comparators.TransportationComparator;
import entity.transport.ActionTime;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import entity.transport.TransportUtil;
import utils.UpdateUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by szpt_user045 on 19.03.2019.
 */

@WebServlet(Branches.API.TRANSPORT_TIME)
public class TransportTimeServletAPI extends ServletAPI {

    private final TransportationComparator comparator = new TransportationComparator();
    final UpdateUtil updateUtil = new UpdateUtil();
    private final Logger log = Logger.getLogger(TransportTimeServletAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject body = parseBody(req);
        if (body != null) {
            Transportation transportation = dao.getObjectById(Transportation.class, body.get(ID));
            TransportDirection direction = TransportDirection.valueOf(String.valueOf(body.get(DIRECTION)));

            log.info("Set time" + direction.toString().toUpperCase() + " for transportation " + transportation.getId());
            comparator.fix(transportation);
            Worker worker = getWorker(req);
            ActionTime time = null;
            switch (direction) {
                case in:
                    time = transportation.getTimeIn();
                    break;
                case out:
                    time = transportation.getTimeOut();
                    break;
            }
            if (time == null) {
                time = new ActionTime(worker);
                switch (direction) {
                    case in:
                        transportation.setTimeIn(time);
                        Date date = Date.valueOf(LocalDate.now());
                        if (!transportation.getDate().equals(date)){
                            transportation.setDate(date);
                        }
                        break;
                    case out:
                        transportation.setTimeOut(time);
                        break;
                }
            }
            dao.save(time);
            dao.saveTransportation(transportation);
            updateUtil.onSave(transportation);
//            TelegramNotificator notificator = TelegramBotFactory.getTelegramNotificator();
//            if (notificator != null) {
//                if (direction == TransportDirection.in && transportation.getTimeOut() == null) {
//                    notificator.transportInto(transportation);
//                }
//            }
            TransportUtil.checkTransport(transportation);
            comparator.compare(transportation, worker);

            body.clear();
            JSONObject json = new SuccessAnswer(TIME, time.getTime().toString()).toJson();
            write(resp, json.toJSONString());
            pool.put(json);
        } else {
            write(resp, EMPTY_BODY);
        }
    }

}

