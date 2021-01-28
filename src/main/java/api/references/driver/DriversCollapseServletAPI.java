package api.references.driver;

import api.ServletAPI;
import constants.Branches;
import entity.transport.Driver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.transport.CollapseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by szpt_user045 on 16.11.2019.
 */
@WebServlet(Branches.API.DRIVERS_COLLAPSE)
public class DriversCollapseServletAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            ArrayList<Driver> drivers = new ArrayList<>();
            for (Object o : (JSONArray)body.get("drivers")){
                JSONObject json = (JSONObject) o;
                drivers.add(dao.getObjectById(Driver.class, json.get(ID)));
            }
            CollapseUtil.collapse(drivers);
            write(resp, SUCCESS_ANSWER);
        }
    }
}
