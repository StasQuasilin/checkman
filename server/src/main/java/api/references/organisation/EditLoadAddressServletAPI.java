package api.references.organisation;

import api.ServletAPI;
import constants.Branches;
import entity.Address;
import entity.documents.LoadPlan;
import entity.organisations.LoadAddress;
import entity.organisations.Organisation;
import org.json.simple.JSONObject;
import utils.AddressUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 29.11.2019.
 */
@WebServlet(Branches.API.References.ADDRESS_EDIT)
public class EditLoadAddressServletAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            LoadAddress loadAddress = dao.getObjectById(LoadAddress.class, body.get(ID));
            if (loadAddress == null){
                loadAddress = new LoadAddress();
                loadAddress.setAddress(new Address());
            }
            Organisation organisation = dao.getObjectById(Organisation.class, body.get(COUNTERPARTY));
            if (loadAddress.getOrganisation() == null || loadAddress.getOrganisation().getId() != organisation.getId()){
                loadAddress.setOrganisation(organisation);
            }
            JSONObject a = (JSONObject) body.get(ADDRESS);
            Address address = loadAddress.getAddress();
            AddressUtil.buildAddress(address, a);
            dao.save(address, loadAddress);
            JSONObject json = new SuccessAnswer(RESULT, loadAddress.toJson()).toJson();
            write(resp, json.toJSONString());
            pool.put(json);
        }
    }
}
