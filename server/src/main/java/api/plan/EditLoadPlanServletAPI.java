package api.plan;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.DealType;
import entity.Person;
import entity.Worker;
import entity.documents.Deal;
import entity.documents.Shipper;
import entity.documents.LoadPlan;
import entity.transport.Driver;
import entity.transport.TransportCustomer;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.DocumentUIDGenerator;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by szpt_user045 on 19.04.2019.
 */
@WebServlet(Branches.API.PLAN_LIST_ADD)
public class EditLoadPlanServletAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(EditLoadPlanServletAPI.class);
    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body );
            Date date = Date.valueOf(String.valueOf(body.get("date")));
            long plan = (long) body.get("plan");
            Shipper shipper = dao.getShipperByValue(body.get("from"));
            long dealId = Long.parseLong(String.valueOf(body.get("deal")));
            Deal deal;
            Worker creator = getWorker(req);

            if (dealId == -1){
                deal = new Deal();
                deal.setUid(DocumentUIDGenerator.generateUID());
                deal.setType(DealType.valueOf(String.valueOf(body.get("type"))));
                deal.setDate(date);
                deal.setDateTo(date);
                deal.setOrganisation(dao.getOrganisationById(body.get("organisation")));
                deal.setShipper(shipper);
                deal.setProduct(dao.getProductById(body.get("product")));
                deal.setQuantity(plan);
                deal.setUnit(dao.getWeightUnitById(body.get("unit")));
                deal.setPrice(Float.parseFloat(String.valueOf(body.get("price"))));
                deal.setCreator(creator);
                dao.saveDeal(deal);
                updateUtil.onSave(deal);
            } else {
                deal = dao.getDealById(dealId);
            }
            long id = -1;
            if (body.containsKey(Constants.ID)) {
                id = (long) body.get(Constants.ID);
            }
            LoadPlan loadPlan;
            Transportation transportation;
            if (id != -1) {
                loadPlan = dao.getLoadPlanById(id);
                transportation = loadPlan.getTransportation();
            } else {
                loadPlan = new LoadPlan();
                loadPlan.setDeal(deal);
                transportation = new Transportation();
                transportation.setUid(DocumentUIDGenerator.generateUID());
                transportation.setProduct(deal.getProduct());
                transportation.setType(deal.getType());
                transportation.setCounterparty(deal.getOrganisation());
                loadPlan.setTransportation(transportation);
            }

            loadPlan.setDate(date);
            transportation.setDate(date);
            loadPlan.setShipper(shipper);
            transportation.setShipper(shipper);
            loadPlan.setPlan(plan);
            loadPlan.setCustomer(TransportCustomer.valueOf(String.valueOf(body.get("customer"))));

            if (!transportation.isArchive()) {
                transportation.setShipper(shipper);
                JSONObject vehicleJson = (JSONObject) body.get("vehicle");
                if (vehicleJson != null){
                    Vehicle vehicle = null;
                    if (vehicleJson.containsKey(Constants.ID)) {
                        long vehicleId = (long) vehicleJson.get("id");
                        if (vehicleId > 0) {
                            vehicle = dao.getVehicleById(vehicleId);
                        } else if (vehicleId == 0){
                            vehicle = new Vehicle();
                            vehicle.setModel(String.valueOf(vehicleJson.get("model")));
                            vehicle.setNumber(String.valueOf(vehicleJson.get("number")));
                            vehicle.setTrailer(String.valueOf(vehicleJson.get("trailer")));
                            dao.save(vehicle);
                        }
                    }
                    if (vehicle != null) {
                        transportation.setVehicle(vehicle);
                    } else if (transportation.getVehicle() != null) {
                        transportation.setVehicle(null);
                    }

                }

                JSONObject driverJson = (JSONObject) body.get("driver");
                if (driverJson !=null){
                    Driver driver = null;
                    if (driverJson.containsKey(Constants.ID)){
                        long driverId = (long) driverJson.get(Constants.ID);
                        if (driverId > 0){
                            driver = dao.getDriverByID(driverId);
                        } else if(driverId == 0){
                            driver = new Driver();
                            Person person = new Person();
                            JSONObject personJson = (JSONObject) driverJson.get("person");
                            person.setForename(String.valueOf(personJson.get("forename")));
                            person.setSurname(String.valueOf(personJson.get("surname")));
                            person.setPatronymic(String.valueOf(personJson.get("patronymic")));

                            driver.setPerson(person);
                            dao.save(person, driver);
                        }
                    }
                    if (driver != null) {
                        transportation.setDriver(driver);
                    } else if (transportation.getDriver() != null) {
                        transportation.setDriver(null);
                    }
                }
                transportation.setCreator(creator);
            }

            dao.saveTransportation(transportation);
            dao.saveLoadPlan(loadPlan);
            updateUtil.onSave(transportation);
            write(resp, answer);

        } else {
            write(resp, emptyBody);
        }
    }
}
