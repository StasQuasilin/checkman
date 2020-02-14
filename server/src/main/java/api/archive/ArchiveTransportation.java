package api.archive;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.documents.LoadPlan;
import entity.notifications.Notification;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import entity.transport.TransportUtil;
import utils.LanguageBase;
import utils.UpdateUtil;
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

    private Notificator notificator = new Notificator();
    private final LanguageBase base = LanguageBase.getBase();
    public static final String SUCCESS_TEXT = "notificator.archived.success";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Transportation transportation = dao.getObjectById(Transportation.class, body.get(ID));
            if (!transportation.isArchive()) {
                TransportUtil.archive(transportation);

                Worker worker = getWorker(req);
                JSONObject json = new Notification(
                        String.format(
                                base.get(worker.getLanguage(), SUCCESS_TEXT),
                                transportation.getDriver().getPerson().getValue(),
                                transportation.getCounterparty().getValue(),
                                transportation.getProduct().getName(),
                                worker.getPerson().getValue())
                ).toJson();
                notificator.sendNotification(json);
                pool.put(json);
            }
        }
    }
}
