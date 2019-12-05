package api.references.organisation;

import api.ServletAPI;
import constants.Branches;
import entity.deal.Contract;
import entity.organisations.Organisation;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 04.12.2019.
 */
@WebServlet(Branches.API.References.FIND_CONTRACTS)
public class FindContractsAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(FindContractsAPI.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Organisation organisation = dao.getObjectById(Organisation.class, body.get(COUNTERPARTY));
            log.info("getContracts for " + organisation.getValue());
            JSONArray array = pool.getArray();
            for (Contract contract : dao.getContractsByOrganisation(organisation)){
                array.add(contract.toJson());
            }
            write(resp, array.toJSONString());
            pool.put(array);
        }
    }
}
