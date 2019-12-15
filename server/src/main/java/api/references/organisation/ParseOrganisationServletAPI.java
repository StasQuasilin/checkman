package api.references.organisation;

import api.ServletAPI;
import constants.Branches;
import entity.Worker;
import entity.organisations.Organisation;
import entity.organisations.OrganisationType;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.answers.SuccessAnswer;
import utils.hibernate.dbDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.PARSE_ORGANISATION)
public class ParseOrganisationServletAPI extends ServletAPI {


    private final Logger log = Logger.getLogger(ParseOrganisationServletAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if (body != null) {
            log.info(body);
            Organisation organisation = parseOrganisation(String.valueOf(body.get(KEY)), dao, getWorker(req));
            JSONObject json = new SuccessAnswer(RESULT, organisation.toJson()).toJson();
            write(resp, json.toJSONString());
            pool.put(json);
        } else {
            write(resp, EMPTY_BODY);
        }
    }

    public static synchronized Organisation parseOrganisation(String origin, dbDAO dao, Worker worker){
        origin = origin.trim().toUpperCase();
        String name = " " + origin + " ";


        List<OrganisationType> typeList = dao.getOrganisationTypeList();
        String[] types = new String[typeList.size()];
        int i = 0;
        for (OrganisationType organisationType : typeList){
            types[i++] = organisationType.getName();
        }
        Pattern pattern = Pattern.compile("^\\s(" + String.join("|", types) + ")\\s|\\s(" + String.join("|", types) + ")\\s$");
        Matcher matcher = pattern.matcher(name.toUpperCase());
        String type = "";
        if (matcher.find()){
            type = matcher.group();
            name = name.replaceFirst(type, "");
        }

        type = type.trim();
        name = name.trim();
        name = name.replaceAll("^[^а-яА-Яa-zA-Z0-9]|[^а-яА-Яa-zA-Z0-9]$", "");

        if (name.isEmpty()) {
            name = origin;
            type = null;
        }

        Organisation organisation = dao.findOrganisation(type, name);

        if (organisation == null) {
            organisation = new Organisation();
            organisation.setType(type);
            organisation.setName(name);
            organisation.setCreate(new ActionTime(worker));
            dao.save(organisation.getCreate());
            dao.save(organisation);
        }

        return organisation;
    }
}
