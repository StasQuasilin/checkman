package api.plan;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.DealType;
import entity.products.Product;
import entity.Worker;
import entity.documents.Deal;
import entity.documents.DocumentOrganisation;
import entity.documents.LoadPlan;
import entity.organisations.Organisation;
import entity.transport.Driver;
import entity.transport.TransportCustomer;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import entity.weight.WeightUnit;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.DocumentUIDGenerator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

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
public class EditLoadPlanAPI extends API {

    private final Logger log = Logger.getLogger(EditLoadPlanAPI.class);


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body );
            Date date = Date.valueOf(String.valueOf(body.get("date")));
            long plan = (long) body.get("plan");
            DocumentOrganisation documentOrganisation = dao.getDocumentOrganisationByValue(body.get("from"));
            Object dealId = body.get("deal");
            Deal deal;
            Worker creator = getWorker(req);

            if (dealId == null){
                deal = new Deal();
                deal.setUid(DocumentUIDGenerator.generateUID());
                deal.setType(DealType.valueOf(String.valueOf(body.get("type"))));
                deal.setDate(date);
                deal.setDateTo(date);
                deal.setOrganisation(dao.getOrganisationById(body.get("organisation")));
                deal.setDocumentOrganisation(documentOrganisation);
                deal.setProduct(dao.getProductById(body.get("product")));
                deal.setQuantity(plan);
                deal.setUnit(dao.getWeightUnitById(body.get("unit")));
                deal.setPrice(Float.parseFloat(String.valueOf(body.get("price"))));
                deal.setCreator(creator);
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
                transportation = new Transportation();
                loadPlan.setTransportation(transportation);
            }

            loadPlan.setDate(date);
            loadPlan.setDeal(deal);
            loadPlan.setDocumentOrganisation(documentOrganisation);
            loadPlan.setPlan(plan);
            loadPlan.setCustomer(TransportCustomer.valueOf(String.valueOf(body.get("customer"))));

            if (!transportation.isArchive()) {
                transportation.setDocumentOrganisation(documentOrganisation);

                long vehicleId = (long) body.get("vehicle");
                if (vehicleId != -1) {
                    transportation.setVehicle(dao.getVehicleById(vehicleId));
                } else if (transportation.getVehicle() != null) {
                    transportation.setVehicle(null);
                }
                long driverId = (long) body.get("driver");
                if (driverId != -1) {
                    transportation.setDriver(dao.getDriverByID(driverId));
                } else if (transportation.getDriver() != null) {
                    transportation.setDriver(null);
                }

                transportation.setCreator(creator);
            }

            dao.saveDeal(deal);
            dao.saveTransportation(transportation);
            dao.saveLoadPlan(loadPlan);
            write(resp, answer);

        } else {
            write(resp, emptyBody);
        }
    }
}
