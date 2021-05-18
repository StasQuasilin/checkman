package api.references.organisation;

import api.ServletAPI;
import constants.Branches;
import entity.organisations.Organisation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 02.12.2019.
 */
@WebServlet(Branches.API.References.FIND_LOAD_ADDRESS)
public class FindLoadAddress extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Organisation organisation = dao.getObjectById(Organisation.class, body.get(COUNTERPARTY));
            JSONArray array = pool.getArray();
            array.addAll(dao.getLoadAddress(organisation).stream().map(address -> address.getAddress().toJson()).collect(Collectors.toList()));
            write(resp, array.toJSONString());
            pool.put(array);
        }
    }
}
