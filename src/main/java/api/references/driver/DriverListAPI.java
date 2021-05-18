package api.references.driver;

import api.ServletAPI;
import constants.Branches;
import entity.transport.Driver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.U;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 16.11.2019.
 */
@WebServlet(Branches.API.References.DRIVER_LIST)
public class DriverListAPI extends ServletAPI {
    private static final String CHAR = "#";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, ArrayList<Driver>> drivers = new HashMap<>();
        for (Driver driver : dao.getObjects(Driver.class)){
            String sub = U.exist(driver.getPerson().getSurname()) ? driver.getPerson().getSurname().substring(0, 1) : CHAR;
            if (!drivers.containsKey(sub)){
                drivers.put(sub, new ArrayList<>());
            }
            drivers.get(sub).add(driver);
        }

        ArrayList<String> keys = new ArrayList<>(drivers.keySet());
        Collections.sort(keys);
        JSONObject json = pool.getObject();
        for (String key : keys){
            JSONArray array = pool.getArray();
            ArrayList<Driver> d = drivers.get(key);
            d.sort((o1, o2) -> o1.getPerson().getSurname().compareTo(o2.getPerson().getSurname()));
            array.addAll(d.stream().map(driver -> driver.toJson()).collect(Collectors.toList()));
            json.put(key, array);
        }
        write(resp, json.toJSONString());
        pool.put(json);
    }
}
