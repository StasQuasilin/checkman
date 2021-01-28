package api.references.organisation;

import api.ServletAPI;
import constants.Branches;
import entity.organisations.*;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.AddressUtil;
import utils.UpdateUtil;
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
public class EditAddressServletAPI extends ServletAPI {

    private final AddressUtil addressUtil = new AddressUtil();
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Organisation organisation = dao.getObjectById(Organisation.class, body.get(COUNTERPARTY));

            Address address = addressUtil.buildAddress((JSONObject) body.get(ADDRESS));

            AddressType type = AddressType.load;
            if (body.containsKey(TYPE)){
                type = AddressType.valueOf(String.valueOf(body.get(TYPE)));
            }

            if (type == AddressType.load){
                LoadAddress loadAddress = dao.getLoadAddress(address);
                if (loadAddress == null){
                    loadAddress = new LoadAddress();
                    loadAddress.setOrganisation(organisation);
                    loadAddress.setAddress(address);
                    dao.save(loadAddress);
                }
            } else {
                LegalAddress legalAddress = dao.getLegalAddress(address);
                if (legalAddress == null){
                    legalAddress = new LegalAddress();
                    legalAddress.setOrganisation(organisation);
                    legalAddress.setAddress(address);
                    dao.save(legalAddress);
                }
            }
            JSONObject json = new SuccessAnswer(RESULT, address.toJson()).toJson();
            write(resp, json.toJSONString());
            pool.put(json);

            for (Transportation t : dao.getTransportationByAddress(address)){
                updateUtil.onSave(t);
            }
        }
    }
}
