package api.references.organisation;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.organisations.Organisation;
import org.json.simple.JSONArray;
import utils.JsonParser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.FIND_ORGANISATION)
public class FindOrganisationAPI extends IAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);

        String key = body.get(Constants.KEY);

        List<Organisation> organisations = hibernator.find(Organisation.class, "name", key);
        JSONArray array = organisations.stream().map(JsonParser::toJson).collect(Collectors.toCollection(JSONArray::new));
        write(resp, array.toJSONString());

        array.clear();
        body.clear();
    }
}
