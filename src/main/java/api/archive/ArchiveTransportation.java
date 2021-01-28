package api.archive;

import api.ServletAPI;
import constants.Branches;
import entity.Worker;
import entity.notifications.Notification;
import entity.transport.TransportUtil;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.LanguageBase;
import utils.notifications.Notificator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kvasik on 26.08.2019.
 */
@WebServlet(Branches.API.ARCHIVE_LOAD_PLAN)
public class ArchiveTransportation extends ServletAPI {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Transportation transportation = dao.getObjectById(Transportation.class, body.get(ID));
            if (!transportation.isArchive()) {
                Worker worker = getWorker(req);
                TransportUtil.archive(transportation, worker);


            }
        }
    }
}
