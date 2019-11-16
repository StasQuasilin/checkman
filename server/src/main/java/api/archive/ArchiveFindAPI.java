package api.archive;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import entity.transport.Transportation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.U;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 01.10.2019.
 */
@WebServlet(Branches.API.ARCHIVE_FIND)
public class ArchiveFindAPI extends ServletAPI {
    
    public static final String TRANSPORTATION = Constants.TRANSPORTATION;
    public static final String DATE = TRANSPORTATION + Constants.SLASH + Constants.DATE;
    public static final String DRIVER = TRANSPORTATION + Constants.SLASH + Constants.DRIVER;
    public static final String ORGANISATION = TRANSPORTATION + Constants.SLASH + Constants.COUNTERPARTY;
    public static final String PRODUCT = TRANSPORTATION + Constants.SLASH + Constants.PRODUCT;
    public static final String VEHICLE = TRANSPORTATION + Constants.SLASH + Constants.VEHICLE;
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            HashMap<String, Object> parameters = new HashMap<>();

            String date = String.valueOf(body.get(Constants.DATE));
            if (U.exist(date)){
                parameters.put(DATE, Date.valueOf(date));
            }

            int driverId = Integer.parseInt(String.valueOf(body.get(Constants.DRIVER)));
            if (driverId > 0){
                parameters.put(DRIVER, driverId);
            }

            int organisationId = Integer.parseInt(String.valueOf(body.get(Constants.ORGANISATION)));
            if (organisationId > 0){
                parameters.put(ORGANISATION, organisationId);
            }

            int productId = Integer.parseInt(String.valueOf(body.get(Constants.PRODUCT)));
            if (productId > 0){
                parameters.put(PRODUCT, productId);
            }

            if (parameters.size() > 0) {
                JSONArray array = pool.getArray();
                array.addAll(dao.query(LoadPlan.class, parameters).stream().map(parser::toJson).collect(Collectors.toList()));
                write(resp, array.toJSONString());
                pool.put(array);
            }

        }
    }
}
