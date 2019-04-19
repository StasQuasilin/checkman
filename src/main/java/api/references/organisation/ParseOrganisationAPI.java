package api.references.organisation;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.organisations.Organisation;
import entity.organisations.OrganisationType;
import org.apache.log4j.Logger;
import org.hibernate.annotations.common.util.impl.Log;
import org.json.simple.JSONObject;
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
        System.out.println(body);

        if (body != null) {
            String value = String.valueOf(body.get("name"));
            log.info("Parse organisation name: \'" + value + "\'");
            value = value.trim();
            value = value.toUpperCase();

            Organisation organisation = new Organisation();
            List<OrganisationType> types = hibernator.query(OrganisationType.class, null);

            for (OrganisationType type : types){
                Pattern pattern = Pattern.compile("\\s" + type.getName() + "|" + type.getName() + "\\s");
                Matcher matcher = pattern.matcher(value.toUpperCase());
                if (matcher.find()){
                    String group = matcher.group();
                    log.info("Organisation type: " + group);
                    organisation.setType(type.getName());
                    value = value.replaceFirst(group, "");
                    value = value.trim();
                    break;
                }
            }

            organisation.setName(value);
            log.info("Organisation name: \'" + value + "\'");

            hibernator.save(organisation);
            write(resp, JsonParser.toJson(organisation).toJSONString());
        } else {
            write(resp, emptyBody);
        }
    }
}
