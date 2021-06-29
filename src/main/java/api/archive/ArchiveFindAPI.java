package api.archive;

import api.ServletAPI;
import api.sockets.handlers.OnSubscribeHandler;
import constants.Branches;
import entity.JsonAble;
import entity.Role;
import entity.answers.Answer;
import entity.answers.ErrorAnswer;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.U;
import utils.answers.SuccessAnswer;
import utils.hibernate.DateContainers.BETWEEN;
import utils.hibernate.DateContainers.LE;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by szpt_user045 on 01.10.2019.
 */
@WebServlet(Branches.API.ARCHIVE_FIND)
public class ArchiveFindAPI extends ServletAPI {
    
    public static final String ORGANISATION_KEY = DEAL_PRODUCT + SLASH + DEAL + SLASH + ORGANISATION;
    public static final String PRODUCT_KEY = DEAL_PRODUCT + SLASH + PRODUCT;
    private static final int COUNT_LIMIT = 10;
    private static final String TRANSPORTATION_DATE = "transportation/date";
    private static final String TRANSPORTATION_DRIVER = "transportation/driver";
    private final Logger logger = Logger.getLogger(ArchiveFindAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            logger.info(body);
            HashMap<String, Object> parameters = new HashMap<>();
            boolean dateLimit = false;
            boolean countLimit = false;
            String date = String.valueOf(body.get(DATE));
            String dateTo = String.valueOf(body.get(DATE_TO));
            
            final boolean e1 = U.exist(date);
            final boolean e2 = U.exist(dateTo);
            if(e1 && e2){
                LocalDate f = Date.valueOf(date).toLocalDate();
                LocalDate t = Date.valueOf(dateTo).toLocalDate();
                final long days = f.toEpochDay() - t.toEpochDay();
                if (days < 32){
                    parameters.put(TRANSPORTATION_DATE, new BETWEEN(Date.valueOf(date), Date.valueOf(dateTo)));
                } else {
                    dateLimit = true;
                    parameters.put(TRANSPORTATION_DATE, new BETWEEN(Date.valueOf(date), Date.valueOf(LocalDate.now().plusMonths(1))));
                }
            } else if (e1){
                parameters.put(TRANSPORTATION_DATE, Date.valueOf(date));
            } else if (e2){
                parameters.put(TRANSPORTATION_DATE, Date.valueOf(dateTo));
            } else {
                parameters.put(TRANSPORTATION_DATE, new LE(Date.valueOf(LocalDate.now())));
                countLimit = true;
            }

            int driverId = Integer.parseInt(String.valueOf(body.get(DRIVER)));
            if (driverId > 0){
                parameters.put(TRANSPORTATION_DRIVER, driverId);
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
            Answer answer;
            if (parameters.size() < 2){
                answer = new ErrorAnswer("Need more parameters!");
            } else {
                answer = new SuccessAnswer();
                JSONArray array = pool.getArray();

                final Role view = getView(req);
                final int i = OnSubscribeHandler.calculateSecureMask(view);
                final List<TransportationProduct> transportationList = countLimit ? dao.limitQuery(TransportationProduct.class, parameters, COUNT_LIMIT) : dao.query(TransportationProduct.class, parameters);
                final LinkedList<Integer> ids = new LinkedList<>();
                for (TransportationProduct product : transportationList){
                    final Transportation transportation = product.getTransportation();
                    final int id = transportation.getId();
                    if (!ids.contains(id)){
                        array.add(transportation.toJson(i));
                        ids.add(id);
                    }
                }

                answer.add(RESULT, array);
                answer.add(LIMIT, dateLimit);
                answer.add(COUNT, countLimit ? COUNT_LIMIT : 0);
            }
            write(resp, answer);
        }
    }
}
