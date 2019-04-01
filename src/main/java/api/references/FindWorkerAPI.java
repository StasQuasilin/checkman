package api.references;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.References.FIND_WORKER)
public class FindWorkerAPI extends IAPI {

    final JSONArray array = new JSONArray();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        String key = (String) body.get(Constants.KEY);

        HashMap<Integer, Worker> result = new HashMap<>();
        find("person/surname", key, result);
        find("person/forename", key, result);
        find("person/patronymic", key, result);

        array.addAll(result.values().stream().map(JsonParser::toJson).collect(Collectors.toList()));

        PostUtil.write(resp, array.toJSONString());
        body.clear();
        array.clear();

    }
    synchronized void find(String key, String value, HashMap<Integer, Worker> result){
        for (Worker worker : hibernator.find(Worker.class, key, value)){
            result.put(worker.getId(), worker);
        }
    }
}
