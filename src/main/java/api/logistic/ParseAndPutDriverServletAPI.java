package api.logistic;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import entity.log.comparators.TransportationComparator;
import entity.transport.Driver;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.UpdateUtil;
import utils.VehicleParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 16.07.2019.
 */
@WebServlet(Branches.API.PARSE_AND_PUT_DRIVER)
public class ParseAndPutDriverServletAPI extends ServletAPI {

    final TransportationComparator transportationComparator = new TransportationComparator();
    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            String key = String.valueOf(body.get(Constants.KEY));
            Driver driver = VehicleParser.parseDriver(key);
            LoadPlan loadPlanById = dao.getLoadPlanById(body.get("transportation"));
            Transportation transportation = loadPlanById.getTransportation();
            transportationComparator.fix(transportation);
            transportation.setDriver(driver);
            dao.saveTransportation(transportation);
            updateUtil.onSave(transportation);
            transportationComparator.compare(transportation, getWorker(req));
        }
    }
}
