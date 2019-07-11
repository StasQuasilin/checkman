package api.laboratory.vro;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.laboratory.subdivisions.vro.OilMassFraction;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 22.05.2019.
 */
@WebServlet(Branches.API.DELETE_OIL_MASS_FRACTION_DRY)
public class OilMassFractionDryDeleteServletAPI extends ServletAPI {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long id = -1;
            if (body.containsKey(Constants.ID)){
                id = Long.parseLong(String.valueOf(body.get(Constants.ID)));
            }
            if (id != -1){
                dao.remove(OilMassFraction.class, id);
            }
        } else {
            write(resp, emptyBody);
        }
    }
}
