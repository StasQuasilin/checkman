package api.archive;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
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
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 01.10.2019.
 */
@WebServlet(Branches.API.ARCHIVE_FIND)
public class ArchiveFindAPI extends ServletAPI {
    
    public static final String ORGANISATION_KEY = DEAL + SLASH + ORGANISATION;
    public static final String PRODUCT_KEY = DEAL + SLASH + PRODUCT;
    private final Logger logger = Logger.getLogger(ArchiveFindAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            logger.info(body);
            HashMap<String, Object> parameters = new HashMap<>();

            String date = String.valueOf(body.get(Constants.DATE));
            if (U.exist(date)){
                parameters.put(DATE, Date.valueOf(date));
            }

            int driverId = Integer.parseInt(String.valueOf(body.get(DRIVER)));
            if (driverId > 0){
                parameters.put(DRIVER, driverId);
            }

            if (body.containsKey(ORGANISATION)) {
                int organisationId = Integer.parseInt(String.valueOf(body.get(ORGANISATION)));
                if (organisationId > 0) {
                    parameters.put(ORGANISATION_KEY, organisationId);
                }
            }


            if (body.containsKey(PRODUCT)) {
                int productId = Integer.parseInt(String.valueOf(body.get(PRODUCT)));
                if (productId > 0) {
                    parameters.put(PRODUCT_KEY, productId);
                }
            }

            if (parameters.size() > 0) {
                JSONArray array = pool.getArray();
                array.addAll(dao.query(Transportation.class, parameters).stream().map(Transportation::toJson).collect(Collectors.toList()));
                write(resp, array.toJSONString());
                pool.put(array);
            }

        }
    }
}
