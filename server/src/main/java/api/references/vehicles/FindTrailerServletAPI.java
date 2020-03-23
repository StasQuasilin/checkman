package api.references.vehicles;

import api.ServletAPI;
import constants.Branches;
import entity.transport.Trailer;
import entity.transport.Transportation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 25.11.2019.
 */
@WebServlet(Branches.API.References.FIND_TRAILER)
public class FindTrailerServletAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        final JSONArray array = new JSONArray();
        if (body != null){
            Object o = body.get(KEY);
            JSONArray array = pool.getArray();
            for (Trailer v : dao.findVehicle(Trailer.class, o)){
                array.add(v.toJson());
            }

            write(resp, array.toJSONString());
            pool.put(array);
        }
    }
}
