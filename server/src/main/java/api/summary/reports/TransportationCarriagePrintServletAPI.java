package api.summary.reports;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.transport.Driver;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.hibernate.DateContainers.BETWEEN;
import utils.hibernate.DateContainers.GE;
import utils.hibernate.DateContainers.LE;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 25.11.2019.
 */
@WebServlet(Branches.API.TRANSPORT_CARRIAGE)
public class TransportationCarriagePrintServletAPI extends ServletAPI{
    private static final String FROM = Constants.FROM;
    private static final String TO = Constants.TO;
    private static final String ORGANISATION = Constants.ORGANISATION;
    private static final String DRIVER = Constants.DRIVER;
    private static final String TRANSPORTATIONS = Constants.TRANSPORTATIONS;
    private final Logger log = Logger.getLogger(TransportationCarriagePrintServletAPI.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            log.info(body);
            HashMap<String, Object> params = new HashMap<>();
            Date from = null, to = null;

            if (body.containsKey(FROM) && body.containsKey(TO)){
                from = Date.valueOf(String.valueOf(body.get(FROM)));
                to = Date.valueOf(String.valueOf(body.get(TO)));
                params.put("date", new BETWEEN(from, to));
            } else if (body.containsKey(FROM)){
                from = Date.valueOf(String.valueOf(body.get(FROM)));
                params.put("date", new GE(from));
            } else if (body.containsKey(TO)){
                to = Date.valueOf(String.valueOf(body.get(TO)));
                params.put("date", new LE(to));
            }
            if (body.containsKey(ORGANISATION)){
                Organisation organisation = dao.getObjectById(Organisation.class, body.get(ORGANISATION));
                req.setAttribute(ORGANISATION, organisation);
                params.put("counterparty", organisation);
            }
            if (body.containsKey(DRIVER)){
                Driver driver = dao.getObjectById(Driver.class, body.get(DRIVER));
                req.setAttribute(DRIVER, driver);
                params.put("driver", driver);
            }
            if (from != null){
                req.setAttribute(FROM, from);
            }
            if (to != null){
                req.setAttribute(TO, to);
            }

            HashMap<Product, ArrayList<Transportation>> hashMap = new HashMap<>();
            for (Transportation t : dao.getObjectsByParams(Transportation.class, params)){
                if (t.getWeight() == null || t.getWeight().getNetto() > 0) {
                    Product product = t.getProduct();
                    if (!hashMap.containsKey(product)) {
                        hashMap.put(product, new ArrayList<>());
                    }
                    hashMap.get(product).add(t);
                }
            }
            for (ArrayList<Transportation> a : hashMap.values()){
                a.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
            }

            req.setAttribute(TRANSPORTATIONS, hashMap);
            req.getRequestDispatcher("/pages/print/transportCarriagePrint.jsp").forward(req, resp);
        }
    }
}
