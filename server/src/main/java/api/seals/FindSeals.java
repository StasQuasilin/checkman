package api.seals;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.seals.Seal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 08.04.2019.
 */
@WebServlet(Branches.API.SEALS_FIND)
public class FindSeals extends ServletAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {

            String key = String.valueOf(body.get(KEY));
            JSONArray array = pool.getArray();
            for (Seal seal : dao.findSeal(key)){
                array.add(seal.toJson());
            }
            write(resp, array.toJSONString());
            pool.put(array);
        }

    }
}
