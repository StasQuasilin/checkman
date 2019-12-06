package api.laboratory;

import api.ServletAPI;
import constants.Branches;
import entity.SubdivisionKey;
import org.json.simple.JSONObject;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by szpt_user045 on 06.12.2019.
 */
@WebServlet(Branches.API.FIND_LABORATORY)
public class LaboratoryFindAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Date date = Date.valueOf(String.valueOf(body.get(DATE)));
            SubdivisionKey key = SubdivisionKey.valueOf(String.valueOf(body.get(KEY)));
        }
    }
}
