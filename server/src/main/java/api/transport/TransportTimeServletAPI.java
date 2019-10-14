package api.transport;

import api.ServletAPI;
import bot.BotFactory;
import bot.Notificator;
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
import java.sql.Timestamp;

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
        TransportDirection direction = TransportDirection.valueOf(req.getParameter("dir"));
        JSONObject body = parseBody(req);
        if (body != null) {
            Object id = body.get(Constants.ID);
            Transportation transportation = dao.getTransportationById(id);
            log.info("Set time" + direction.toString().toUpperCase() + " for transportation " + transportation.getId());
            comparator.fix(transportation);
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
                time = new ActionTime();
                switch (direction) {
                    case in:
                        transportation.setTimeIn(time);
                        break;
                    case out:
                        transportation.setTimeOut(time);
                        break;
                }
            }
            Worker worker = getWorker(req);
            time.setTime(new Timestamp(System.currentTimeMillis()));
            time.setCreator(worker);
            dao.save(time);
            dao.saveTransportation(transportation);
            updateUtil.onSave(transportation);
            Notificator notificator = BotFactory.getNotificator();
            if (notificator != null) {
                if (direction == TransportDirection.in) {
                    notificator.transportInto(transportation);
                }
            }
            TransportUtil.checkTransport(transportation);
            comparator.compare(transportation, worker);
//            WeightUtil.calculateDealDone(plan.getDeal());

            body.clear();
            IAnswer answer = new SuccessAnswer();
            answer.add("time", time.getTime().toString());
            write(resp, parser.toJson(answer).toJSONString());
        } else {
            write(resp, emptyBody);
        }
    }

}

