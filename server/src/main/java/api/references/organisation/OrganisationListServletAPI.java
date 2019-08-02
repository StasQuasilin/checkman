package api.references.organisation;

import api.ServletAPI;
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
public class OrganisationListServletAPI extends ServletAPI {

    final HashMap<String,Object> parameters = new HashMap<>();
    final LE le = new LE(Date.valueOf(LocalDate.now()));


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HashMap<Integer, Organisation> organisations = new HashMap<>();
        for (LoadPlan plan : dao.getLastPlans()){
            Organisation organisation = plan.getDeal().getOrganisation();
            if (!organisations.containsKey(organisation.getId())){
                organisations.put(organisation.getId(), organisation);
            }
        }

        write(resp, parser.toJson(organisations.values()).toJSONString());
    }
}
