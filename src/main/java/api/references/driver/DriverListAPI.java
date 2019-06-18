package api.references.driver;

import api.API;
import constants.Branches;
import entity.documents.LoadPlan;
import entity.transport.Driver;
import entity.transport.Vehicle;
import utils.JsonParser;
import utils.hibernate.DateContainers.LE;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.DRIVER_LIST)
public class DriverListAPI extends API {

    final HashMap<String,Object> parameters = new HashMap<>();
    final LE le = new LE(Date.valueOf(LocalDate.now()));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        le.setDate(Date.valueOf(LocalDate.now().plusYears(1)));
        parameters.put("date", le);
        HashMap<Integer, Driver> drivers = new HashMap<>();
        HashMap<Integer, Vehicle> vehicles = new HashMap<>();
        for (LoadPlan plan : hibernator.limitQuery(LoadPlan.class, parameters, 200)){
            Driver driver = plan.getTransportation().getDriver();
            if (driver != null) {
                if (!drivers.containsKey(driver.getId())){
                    drivers.put(driver.getId(), driver);
                }
                Vehicle vehicle = plan.getTransportation().getVehicle();
                if (vehicle != null) {
                    if (!vehicles.containsKey(driver.getId())){
                        vehicles.put(driver.getId(), vehicle);
                    }
                }
            }
        }

        write(resp, JsonParser.toJson(drivers.values(), vehicles).toJSONString());

    }
}
