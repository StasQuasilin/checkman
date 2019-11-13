package api.plan;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Person;
import entity.Worker;
import entity.answers.IAnswer;
import entity.deal.ContractProduct;
import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.documents.LoadPlan;
import entity.log.comparators.LoadPlanComparator;
import entity.log.comparators.TransportationComparator;
import entity.transport.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.*;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.PLAN_LIST_SAVE)
public class SaveLoadPlanServletAPI extends ServletAPI {

    final Logger log = Logger.getLogger(SaveLoadPlanServletAPI.class);
    final TransportationComparator transportationComparator = new TransportationComparator();
    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if(body != null) {
            log.info(body);
            Worker worker = getWorker(req);
            ContractProduct contractProduct = dao.getObjectById(ContractProduct.class, body.get(PRODUCT));
            Transportation2 transportation = dao.getObjectById(Transportation2.class, body.get(ID));
            TransportationProduct transportationProduct = null;
            if (transportation == null){
                transportation = TransportUtil.newTransportation();
                transportation.setCreateTime(new ActionTime(worker));
                transportation.setShipper(contractProduct.getShipper());
                transportation.setManager(worker);
            } else {
                transportationProduct = dao.getTransportationProduct(contractProduct, transportation);
            }

            if (transportationProduct == null){
                transportationProduct = new TransportationProduct();
                transportationProduct.setContractProduct(contractProduct);
                transportationProduct.setTransportation(transportation);
            }

            boolean save = false;

            Date date = Date.valueOf(String.valueOf(body.get(Constants.DATE)));
            log.info("\t...Date: \'" + date.toString() + "\'");
            if (transportation.getDate() == null || !transportation.getDate().equals(date)) {
                transportation.setDate(date);
                save = true;
            }

            float plan = Float.parseFloat(String.valueOf(body.get(Constants.PLAN)));
            log.info("\t...Plan: \'" + plan + "\'");
            if (transportationProduct.getPlan() != plan) {
                transportationProduct.setPlan(plan);
                save = true;
            }

            TransportCustomer customer = TransportCustomer.valueOf(String.valueOf(body.get(Constants.CUSTOMER)));
            log.info("\t...Customer: \'" + customer.toString() + "\'");
            if (transportation.getCustomer() != customer) {
                transportation.setCustomer(customer);
                save = true;
            }
            JSONObject driverJson = (JSONObject) body.get(Constants.DRIVER);
            Driver driver = dao.getObjectById(Driver.class, driverJson.get(ID));
            if (driver != null){
                if (transportation.getDriver() == null || transportation.getDriver().getId() != driver.getId()) {
                    TransportUtil.setDriver(transportation, driver);
                    save = true;
                }
            }

            JSONObject truckJson = (JSONObject) body.get(Constants.TRUCK);
            Truck truck = dao.getObjectById(Truck.class, truckJson.get(ID));
            if (truck != null){
                if (transportation.getTruck() == null || transportation.getTruck().getId() != truck.getId()) {
                    TransportUtil.setTruck(transportation, truck);
                    save = true;
                }
            }

            JSONObject trailerJson = (JSONObject) body.get(Constants.TRAILER);
            Trailer trailer = dao.getObjectById(Trailer.class, trailerJson.get(ID));
            if (trailer != null) {
                if (transportation.getTrailer() == null || transportation.getTrailer().getId() != trailer.getId()){
                    TransportUtil.setTrailer(transportation, trailer);
                    save = true;
                }
            }

            if (save) {
                dao.save(transportation.getCreateTime());
                dao.save(transportation);
                dao.save(transportationProduct);

//todo                updateUtil.onSave(transportation);
            }

            IAnswer answer = new SuccessAnswer(ID, transportation.getId());
            JSONObject json = answer.toJson();
            write(resp, json.toJSONString());
            pool.put(json);
            body.clear();
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}