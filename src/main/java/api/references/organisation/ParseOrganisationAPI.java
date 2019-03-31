package api.references.organisation;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.organisations.Organisation;
import entity.organisations.OrganisationType;
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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);

        Organisation organisation = parse(String.valueOf(body.get(Constants.NAME)));

        hibernator.save(organisation);
        JSONObject json = JsonParser.toJson(organisation);
        write(resp, json.toJSONString());
        json.clear();

    }

    public static synchronized Organisation parse(String value){
        Organisation organisation = new Organisation();
        List<OrganisationType> types = hibernator.query(OrganisationType.class, null);

        value = value.trim();
        value = value.toUpperCase();

        for (OrganisationType type : types){
            Pattern pattern = Pattern.compile("\\s" + type.getName() + "|" + type.getName() + "\\s");
            Matcher matcher = pattern.matcher(value.toUpperCase());
            if (matcher.find()){

                String group = matcher.group();
                organisation.setType(type.getName());
                value = value.replaceFirst(group, "");
                value = value.trim();
                break;
            }
        }

        organisation.setName(value);

        return organisation;
    }
}
