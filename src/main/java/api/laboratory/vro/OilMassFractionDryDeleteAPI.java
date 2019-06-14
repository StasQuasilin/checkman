package api.laboratory.vro;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.laboratory.subdivisions.vro.OilMassFractionDry;
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
public class OilMassFractionDryDeleteAPI extends API {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long id = -1;
            if (body.containsKey(Constants.ID)){
                id = Long.parseLong(String.valueOf(body.get(Constants.ID)));
            }
            if (id != -1){
                OilMassFractionDry omf = hibernator.get(OilMassFractionDry.class, "id", id);
                hibernator.remove(omf);
            }
        } else {
            write(resp, emptyBody);
        }
    }
}
