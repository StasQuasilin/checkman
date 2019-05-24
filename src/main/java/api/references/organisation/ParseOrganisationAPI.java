package api.references.organisation;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.answers.ErrorAnswer;
import entity.organisations.Organisation;
import entity.organisations.OrganisationType;
import org.apache.log4j.Logger;
import org.hibernate.annotations.common.util.impl.Log;
import org.json.simple.JSONObject;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import utils.JsonParser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.PARSE_ORGANISATION)
public class ParseOrganisationAPI extends IAPI {

    private final Logger log = Logger.getLogger(ParseOrganisationAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if (body != null) {
            log.info(body);
            String origin = String.valueOf(body.get("name"));
            log.info("Parse organisation name: \'" + origin + "\'");
            origin = origin.trim().toUpperCase();
            String name = " " + origin + " ";

            List<OrganisationType> typeList = hibernator.query(OrganisationType.class, null);
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
            log.info("Organisation type: \'" + type + "\'");
            log.info("Organisation name: \'" + name + "\'");
            HashMap<String, String> param = new HashMap<>();
            param.put("type", type);
            param.put("name", name);

            if (name.isEmpty()) {
                name = origin;
                type = null;
            }

            Organisation organisation = null;
            List<Organisation> organisations = hibernator.find(Organisation.class, param);
            if (organisations.size() > 0) {
                organisation = organisations.get(0);
            }
            if (organisation == null) {
                organisation = new Organisation();
                organisation.setType(type);
                organisation.setName(name);
                hibernator.save(organisation);
            }
            write(resp, JsonParser.toJson(organisation).toJSONString());
        } else {
            write(resp, emptyBody);
        }
    }
}
