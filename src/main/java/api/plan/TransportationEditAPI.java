package api.plan;

import api.ServletAPI;
import constants.Branches;
import entity.Worker;
import org.json.simple.JSONObject;
import utils.transport.TransportationEditor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 19.04.2019.
 */
@WebServlet(Branches.API.PLAN_LIST_ADD)
public class TransportationEditAPI extends ServletAPI {

    private TransportationEditor transportationEditor = new TransportationEditor();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            System.out.println(body);
            Worker creator = getWorker(req);
            transportationEditor.saveTransportation(body, creator);
            write(resp, SUCCESS_ANSWER);

//            dao.getUsedStoragesByTransportation(transportation).forEach(storageUtil::updateStorageEntry);
//            List<TransportStorageUsed> u = dao.getUsedStoragesByTransportation(transportation);
//            TransportUtil.updateUsedStorages(transportation, u, getWorker(req));
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
