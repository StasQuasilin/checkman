package api.transport;

import api.ServletAPI;
import constants.Branches;
import entity.transport.ActionTime;
import entity.transport.TransportUtil;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.UpdateUtil;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet(Branches.API.SAVE_CUSTOM_TIME)
public class SaveCustomTime extends ServletAPI {

    private final dbDAO dao = dbDAOService.getDAO();
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Timestamp date = Timestamp.valueOf(String.valueOf(body.get(DATE)));
            TransportDirection direction = TransportDirection.valueOf(String.valueOf(body.get(ACTION)));
            final Transportation transportation = dao.getObjectById(Transportation.class, body.get(ID));
            ActionTime actionTime;
            switch (direction){
                case in:
                    actionTime = transportation.getTimeIn();
                break;
                case out:
                    actionTime = transportation.getTimeOut();
                    break;
                default:
                    actionTime = transportation.getTimeRegistration();
                    break;
            }

            if (actionTime == null){
                actionTime = new ActionTime();
                actionTime.setCreator(getWorker(req));
                switch (direction){
                    case in:
                        transportation.setTimeIn(actionTime);
                        break;
                    case out:
                        transportation.setTimeOut(actionTime);
                        break;
                    default:transportation.setTimeRegistration(actionTime);
                }
            }
            actionTime.setTime(date);
            dao.save(actionTime);
            dao.save(transportation);
            updateUtil.onSave(transportation);
            write(resp, SUCCESS_ANSWER);
            TransportUtil.checkTransport(transportation);
        }
    }
}
