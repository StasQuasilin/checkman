package api.summary.reports;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.hibernate.DateContainers.BETWEEN;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by szpt_user045 on 25.11.2019.
 */
@WebServlet(Branches.API.TRANSPORT_COST)
public class TransportationCostPrintAPI extends ServletAPI{
    private static final String FROM = Constants.FROM;
    private static final String TO = Constants.TO;
    private static final String ORGANISATION = Constants.ORGANISATION;
    private static final String DRIVER = Constants.DRIVER;
    private static final String TRANSPORTATIONS = Constants.TRANSPORTATIONS;
    private final Logger log = Logger.getLogger(TransportationCostPrintAPI.class);
    private static final LocalTime time = LocalTime.of(8, 0);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            log.info(body);
            HashMap<String, Object> params = new HashMap<>();
            Timestamp from = null, to = null;

            if (body.containsKey(FROM) && body.containsKey(TO)){
                LocalDateTime f = LocalDateTime.of(LocalDate.parse(String.valueOf(body.get(FROM))), time);
                LocalDateTime t = LocalDateTime.of(LocalDate.parse(String.valueOf(body.get(TO))).plusDays(1), time);

                from = Timestamp.valueOf(f);
                to = Timestamp.valueOf(t);

                params.put("timeIn/time", new BETWEEN(from, to));
            }

            if (body.containsKey(ORGANISATION)){
                Organisation organisation = dao.getObjectById(Organisation.class, body.get(ORGANISATION));
                req.setAttribute(ORGANISATION, organisation);
                params.put("deal/organisation", organisation);
            }

            ArrayList<Transportation> transportations = new ArrayList<>();
            if (body.containsKey(PRODUCT)){
                Product product = dao.getObjectById(Product.class, body.get(PRODUCT));
                log.info("by Product " + product.getName());
                params.put(PRODUCT, product.getId());
            }
            if (body.containsKey("vehicleContain")){
                Object key = body.get("vehicleContain");
                List<Vehicle> vehicleContain = dao.getVehiclesByName(key);
                log.info("Vehicles, contain " + key.toString() + ": " + vehicleContain.size());
                for (Vehicle v : vehicleContain){
                    HashMap<String, Object> map = new HashMap<>(params);
                    map.put(VEHICLE, v);
                    transportations.addAll(dao.getObjectsByParams(Transportation.class, map));
                }
            } else {
                JSONArray array = (JSONArray)body.get(DRIVERS);
                if(array.size() > 0){
                    for (Object o : array){
                        params.put(DRIVER, o);
                        transportations.addAll(dao.getObjectsByParams(Transportation.class, params));
                    }
                } else {
                    transportations.addAll(dao.getObjectsByParams(Transportation.class, params));
                }
            }
            if (from != null){
                req.setAttribute(FROM, from);
            }
            if (to != null){
                req.setAttribute(TO, to);
            }

            HashMap<Integer, Product> productHashMap = new HashMap<>();
            HashMap<Integer, ArrayList<Transportation>> hashMap = new HashMap<>();
            for (Transportation t : transportations){
//                Product product = t.getProduct();
//                int id = product.getId();
//                if (!productHashMap.containsKey(id)){
//                    productHashMap.put(id, product);
//                }
//                if (!hashMap.containsKey(id)) {
//                    hashMap.put(id, new ArrayList<>());
//                }
//                hashMap.get(id).add(t);
            }

            HashMap<Product, ArrayList<Transportation>> result = new HashMap<>();
            for (Map.Entry<Integer, ArrayList<Transportation>> entry : hashMap.entrySet()){
                Product product = productHashMap.get(entry.getKey());
                if (!result.containsKey(product)){
                    result.put(product, new ArrayList<>());
                }
                result.get(product).addAll(entry.getValue());
            }
            hashMap.clear();
            productHashMap.clear();
            for (ArrayList<Transportation> a : result.values()){
                a.sort((t1, t2) -> {
                    final Date d1 = t1.getDate();
                    final Date d2 = t2.getDate();
                    if (d1.equals(d2)){
                        final Timestamp time1 = t1.getTimeIn().getTime();
                        final Timestamp time2 = t2.getTimeIn().getTime();
                        if (time1 == null){
                            return 1;
                        } else if (time2 == null){
                            return -1;
                        } else {
                            return time2.compareTo(time1);
                        }
                    } else {
                        return d2.compareTo(d1);
                    }
                });
            }

            req.setAttribute(TRANSPORTATIONS, result);
            req.getRequestDispatcher("/pages/print/transportCostPrint.jsp").forward(req, resp);
        }
    }
}
