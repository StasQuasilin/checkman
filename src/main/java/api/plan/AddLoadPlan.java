package api.plan;

import api.IAPI;
import constants.Branches;
import entity.DealType;
import entity.Product;
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
import org.json.simple.JSONObject;
import utils.DocumentUIDGenerator;

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
public class AddLoadPlan extends IAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        System.out.println(body );
        if (body != null) {
            Date date = Date.valueOf(String.valueOf(body.get("date")));
            long plan = (long) body.get("plan");
            DocumentOrganisation documentOrganisation = hibernator.get(DocumentOrganisation.class, "value", body.get("from"));
            long dealId = Long.parseLong(String.valueOf(body.get("deal")));
            Deal deal;
            Worker creator = getWorker(req);

            if (dealId == -1){
                deal = new Deal();
                deal.setUid(DocumentUIDGenerator.generateUID());
                deal.setType(DealType.valueOf(String.valueOf(body.get("type"))));
                deal.setDate(date);
                deal.setDateTo(date);
                deal.setOrganisation(hibernator.get(Organisation.class, "id", body.get("organisation")));
                deal.setDocumentOrganisation(documentOrganisation);
                deal.setProduct(hibernator.get(Product.class, "id", body.get("product")));
                deal.setQuantity(plan);
                deal.setUnit(hibernator.get(WeightUnit.class, "id", body.get("unit")));
                deal.setPrice(Float.parseFloat(String.valueOf(body.get("price"))));
                deal.setCreator(creator);
            } else {
                deal = hibernator.get(Deal.class, "id", dealId);
            }

            LoadPlan loadPlan = new LoadPlan();
            loadPlan.setDate(date);
            loadPlan.setDeal(deal);
            loadPlan.setDocumentOrganisation(documentOrganisation);
            loadPlan.setPlan(plan);
            loadPlan.setCustomer(TransportCustomer.valueOf(String.valueOf(body.get("customer"))));

            Transportation transportation = new Transportation();
            transportation.setDocumentOrganisation(documentOrganisation);

            long vehicleId = (long) body.get("vehicle");
            if (vehicleId != -1) {
                transportation.setVehicle(hibernator.get(Vehicle.class, "id", vehicleId));
            }
            long driverId = (long) body.get("driver");
            if (driverId != -1) {
                transportation.setDriver(hibernator.get(Driver.class, "id", driverId));
            }

            transportation.setCreator(creator);
            loadPlan.setTransportation(transportation);
            hibernator.save(deal);
            hibernator.save(transportation, loadPlan);
            write(resp, answer);

        } else {
            write(resp, emptyBody);
        }
    }
}
