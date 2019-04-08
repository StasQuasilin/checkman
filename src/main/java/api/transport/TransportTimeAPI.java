package api.transport;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.answers.IAnswer;
import entity.transport.ActionTime;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;
import utils.TransportUtil;
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
public class TransportTimeAPI extends IAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TransportDirection direction = TransportDirection.valueOf(req.getParameter("dir"));
        JSONObject body = PostUtil.parseBodyJson(req);
        long id = (long) body.get(Constants.ID);
        Transportation transportation = hibernator.get(Transportation.class, "id", id);
        ActionTime time = null;
        switch (direction){
            case in:
                time = transportation.getTimeIn();
                break;
            case out:
                time = transportation.getTimeOut();
                break;
        }
        if (time == null) {
            time = new ActionTime();
            switch (direction){
                case in:
                    transportation.setTimeIn(time);
                    break;
                case out:
                    transportation.setTimeOut(time);
                    break;
            }
        }
        time.setTime(new Timestamp(System.currentTimeMillis()));
        time.setCreator(getWorker(req));
        TransportUtil.checkTransport(transportation);
        hibernator.save(time, transportation);
        body.clear();
        IAnswer answer = new SuccessAnswer();
        answer.add("time", time.getTime().toString());
        write(resp, JsonParser.toJson(answer).toJSONString());
    }

}

