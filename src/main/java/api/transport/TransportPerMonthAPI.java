package api.transport;

import api.ServletAPI;
import constants.Branches;
import entity.products.Product;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by szpt_user045 on 29.01.2020.
 */
@WebServlet(Branches.API.TRANSPORT_PER_MONTH)
public class TransportPerMonthAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null){
            Date from = Date.valueOf(String.valueOf(body.get(FROM)));
            Date to = Date.valueOf(String.valueOf(body.get(TO)));
            HashMap<Product, Integer> map = new HashMap<>();

            for (Transportation transportation : dao.getTransportationsByDate(from, to)){
                for (TransportationProduct p : transportation.getProducts()){
                    final Product product = p.getDealProduct().getProduct();
                    if (!map.containsKey(product)){
                        map.put(product, 0);
                    }
                    map.put(product, map.get(product) + 1);
                }
            }
            JSONArray array = pool.getArray();
            for (Map.Entry<Product, Integer> entry : map.entrySet()){
                JSONObject e = entry.getKey().toJson();
                e.put(AMOUNT, entry.getValue());
                array.add(e);
            }
            write(resp, array.toJSONString());
            pool.put(array);
        }
    }
}
