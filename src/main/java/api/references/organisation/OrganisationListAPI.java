package api.references.organisation;

import api.API;
import constants.Branches;
import entity.documents.LoadPlan;
import entity.organisations.Organisation;
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
 * Created by szpt_user045 on 24.04.2019.
 */
@WebServlet(Branches.API.References.ORGANISATION_LIST)
public class OrganisationListAPI extends API {

    final HashMap<String,Object> parameters = new HashMap<>();
    final LE le = new LE(Date.valueOf(LocalDate.now()));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        le.setDate(Date.valueOf(LocalDate.now().plusYears(1)));
        parameters.put("date", le);
        HashMap<Integer, Organisation> organisations = new HashMap<>();
        for (LoadPlan plan : hibernator.limitQuery(LoadPlan.class, parameters, 200)){
            Organisation organisation = plan.getDeal().getOrganisation();
            if (!organisations.containsKey(organisation.getId())){
                organisations.put(organisation.getId(), organisation);
            }
        }

        write(resp, JsonParser.toJson(organisations.values()).toJSONString());
    }
}
