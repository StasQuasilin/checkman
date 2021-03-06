package api.warehousing;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.documents.Shipper;
import entity.transport.ActionTime;
import entity.transport.TransportStorageUsed;
import entity.transport.Transportation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.UpdateUtil;
import utils.storages.StorageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kvasik on 14.10.2019.
 */
@WebServlet(Branches.API.WAREHOUSING_EDIT)
public class WarehousingEditAPI extends ServletAPI {
    private static final String DOCUMENT = Constants.DOCUMENT;
    private static final String RELATIONS = "relations";
    private static final String SHIPPER = Constants.SHIPPER;

    private final UpdateUtil updateUtil = new UpdateUtil();
    private final StorageUtil storageUtil = new StorageUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Worker worker = getWorker(req);
            System.out.println(body);
            Transportation transportation = dao.getTransportationById(body.get(DOCUMENT));
            HashMap<Integer, TransportStorageUsed> used = new HashMap<>();

            List<TransportStorageUsed> usedList = dao.getUsedStoragesByTransportation(transportation);
            for (TransportStorageUsed u : usedList){
                used.put(u.getId(), u);
            }

            ArrayList<TransportStorageUsed> save = new ArrayList<>();
            ArrayList<TransportStorageUsed> remove = new ArrayList<>();

            for (Object o : (JSONArray)body.get(RELATIONS)){
                JSONObject u = (JSONObject) o;
                TransportStorageUsed tsu;
                if (u.containsKey(ID)){
                    tsu = used.remove(Integer.parseInt(String.valueOf(u.get(ID))));
                } else {
                    tsu = new TransportStorageUsed();
                    tsu.setTransportation(transportation);
                }

                int shipperId = Integer.parseInt(String.valueOf(u.get(SHIPPER)));
                if (tsu.getShipper() == null || tsu.getShipper().getId() != shipperId){
                    Shipper shipper;

                    if (!save.contains(tsu)){
                        save.add(tsu);
                    }
                }

                int storageId = Integer.parseInt(String.valueOf(u.get(STORAGE)));
                if (tsu.getStorage() == null || tsu.getStorage().getId() != storageId){
//                    tsu.setStorage(dao.getStorageById(storageId));
                    if (!save.contains(tsu)){
                        save.add(tsu);
                    }
                }

                float amount = Float.parseFloat(String.valueOf(u.get(AMOUNT)));
                if (amount == 0){
                    if (tsu.getId() > 0){
                        remove.add(tsu);
                    }
                } else if (tsu.getAmount() != amount){
                    tsu.setAmount(amount);
                    if (!save.contains(tsu)){
                        save.add(tsu);
                    }
                }
            }

            remove.addAll(used.values().stream().collect(Collectors.toList()));
            used.clear();

            for (TransportStorageUsed r : remove){
                dao.remove(r);
                storageUtil.removeStorageEntry(r);
            }

            for (TransportStorageUsed u : save){
                if (u.getCreate() == null){
                    ActionTime time = new ActionTime(worker);
                    dao.save(time);
                    u.setCreate(time);
                }
                dao.save(u);
                storageUtil.updateStorageEntry(u);
            }
            if (save.size() > 0){
                updateUtil.onSave(transportation);
            }

            save.clear();
            remove.clear();

            write(resp, SUCCESS_ANSWER);

        }
    }
}
