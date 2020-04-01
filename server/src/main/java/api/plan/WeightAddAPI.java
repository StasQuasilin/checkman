package api.plan;

import api.ServletAPI;
import api.deal.DealEditor;
import constants.Branches;
import entity.Worker;
import entity.documents.Deal;
import entity.documents.Shipper;
import entity.log.comparators.DealComparator;
import entity.log.comparators.TransportComparator;
import entity.organisations.Address;
import entity.organisations.Organisation;
import entity.transport.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.NoteUtil;
import utils.U;
import utils.UpdateUtil;
import utils.storages.StorageUtil;
import utils.transport.TransportationEditor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 19.04.2019.
 */
@WebServlet(Branches.API.PLAN_LIST_ADD)
public class WeightAddAPI extends ServletAPI {

    private DealEditor dealEditor = new DealEditor();
    private TransportationEditor transportationEditor = new TransportationEditor();

    private final StorageUtil storageUtil = new StorageUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            Worker creator = getWorker(req);
            Worker manager = dao.getObjectById(body.get(MANAGER));
            if (manager == null){
                manager = creator;
            }

            Deal deal = dealEditor.editDeal((JSONObject) body.get(DEAL), manager);
            Transportation transportation = transportationEditor.saveTransportation(deal, body, creator, manager);

            dao.save(transportation);
            dao.getUsedStoragesByTransportation(transportation).forEach(storageUtil::updateStorageEntry);

            write(resp, SUCCESS_ANSWER);
            List<TransportStorageUsed> u = dao.getUsedStoragesByTransportation(transportation);
            TransportUtil.updateUsedStorages(transportation, u, getWorker(req));
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
