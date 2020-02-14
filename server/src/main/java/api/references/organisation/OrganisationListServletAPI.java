package api.references.organisation;

import api.ServletAPI;
import constants.Branches;
import entity.organisations.Organisation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.U;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 24.04.2019.
 */
@WebServlet(Branches.API.References.ORGANISATION_LIST)
public class OrganisationListServletAPI extends ServletAPI {

    public static final String CHAR = "#";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, List<Organisation>> organisations = new HashMap<>();
        for (Organisation organisation : dao.getOrganisations()){

            String substring = U.exist(organisation.getName()) ? getChar(organisation.getName()) : CHAR;
            if (!organisations.containsKey(substring)){
                organisations.put(substring, new ArrayList<>());
            }
            organisations.get(substring).add(organisation);
        }

        List<String> keys = new ArrayList<>(organisations.keySet());
        Collections.sort(keys);
        JSONObject json = pool.getObject();
        for (String key : keys){
            JSONArray array = pool.getArray();
            array.addAll(organisations.get(key).stream().map(parser::toJson).collect(Collectors.toList()));
            json.put(key, array);
        }
        write(resp, json.toJSONString());
        pool.put(json);
    }
    synchronized String getChar(String s){
        for (char c : s.toCharArray()){
            if (Character.isLetter(c)){
                return String.valueOf(c);
            }
        }
        return CHAR;
    }
}
