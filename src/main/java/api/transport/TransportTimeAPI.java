package api.transport;

import api.API;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.answers.IAnswer;
import entity.documents.LoadPlan;
import entity.log.comparators.TransportationComparator;
import entity.transport.ActionTime;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.TransportUtil;
import utils.WeightUtil;
import utils.answers.SuccessAnswer;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

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
public class TransportTimeAPI extends API {

    private final TransportationComparator comparator = new TransportationComparator();
    dbDAO dao = dbDAOService.getDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TransportDirection direction = TransportDirection.valueOf(req.getParameter("dir"));
        JSONObject body = parseBody(req);
        if (body != null) {
            Object id = body.get(Constants.ID);
            LoadPlan plan = dao.getLoadPlanByTransportationId(id);
            Transportation transportation = plan.getTransportation();
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
            dao.saveTransportation(time, transportation);
            Notificator notificator = BotFactory.getNotificator();
            if (notificator != null) {
                notificator.transportShow(plan);
            }
            TransportUtil.checkTransport(transportation);
            comparator.compare(transportation, worker);
            WeightUtil.calculateDealDone(plan.getDeal());

            body.clear();
            IAnswer answer = new SuccessAnswer();
            answer.add("time", time.getTime().toString());
            write(resp, JsonParser.toJson(answer).toJSONString());
        } else {
            write(resp, emptyBody);
        }
    }

}

