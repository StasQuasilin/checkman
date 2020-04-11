package api.transport;

import api.ServletAPI;
import api.deal.DealEditor;
import api.deal.FastDealEditor;
import constants.Branches;
import entity.Worker;
import entity.documents.Deal;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.transport.TransportationEditor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.API.TRANSPORTATION_SAVE_FAST)
public class FastTransportAPI extends ServletAPI {

    private final FastDealEditor dealEditor = new FastDealEditor();
    private final TransportationEditor transportationEditor = new TransportationEditor();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Worker worker = getWorker(req);
            Worker manager = dao.getObjectById(Worker.class, body.get(MANAGER));
            if (manager == null){
                manager = worker;
            }

            Deal deal = dealEditor.editDeal((JSONObject) body.get(DEAL), worker);

            transportationEditor.saveTransportation(deal, body, worker, manager);
            write(resp, SUCCESS_ANSWER);
        }
    }
}
