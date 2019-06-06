package api.laboratory.storages;

import api.IAPI;
import constants.Branches;
import entity.AnalysesType;
import entity.laboratory.storages.StorageAnalyses;
import entity.laboratory.storages.StorageTurn;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.production.Turn;
import entity.storages.Storage;
import entity.storages.StorageProduct;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import utils.JsonParser;
import utils.hibernate.DateContainers.BETWEEN;
import utils.hibernate.DateContainers.LE;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 06.06.2019.
 */
@WebServlet(Branches.API.LABORATORY_STORAGE_LIST)
public class StorageListAPI extends IAPI {

    private final int LIMIT = 15;
    final HashMap<AnalysesType, List<StorageProduct>> products = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject array = new JSONObject();
        final JSONArray add = new JSONArray();
        final JSONArray update = new JSONArray();
        final JSONArray remove= new JSONArray();
        array.put("add", add);
        array.put("update", update);
        array.put("remove", remove);

        JSONObject body = parseBody(req);
        if (body != null) {

            final HashMap<String, Object> parameters = new HashMap<>();
            if (body.containsKey("reqDate")){
                String date = String.valueOf(body.remove("reqDate"));
                LocalDate localDate = LocalDate.parse(date);
                final BETWEEN between = new BETWEEN(Date.valueOf(localDate), Date.valueOf(localDate.plusDays(1)));
                parameters.put("turn/date", between);
            } else {
                final LE le = new LE(Date.valueOf(LocalDate.now()));
                le.setDate(Date.valueOf(LocalDate.now().plusYears(1)));
                parameters.put("turn/date", le);
            }

            for (StorageTurn turn : hibernator.limitQuery(StorageTurn.class, parameters, LIMIT)){
                String id = String.valueOf(turn.getId());
                if(body.containsKey(id)){
                    long hash = (long) body.remove(id);
                    if (turn.hashCode() != hash){
                        update.add(JsonParser.toJson(turn));
                    }
                } else {
                    add.add(JsonParser.toJson(turn));
                }
            }

            remove.addAll((Collection) body.keySet().stream().map(o -> Integer.parseInt(String.valueOf(o))).collect(Collectors.toList()));
        }

        write(resp, array.toJSONString());
        add.clear();
        update.clear();
        remove.clear();
    }


}
