package api.references.products;

import api.ServletAPI;
import constants.Branches;
import entity.products.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 29.11.2019.
 */
@WebServlet(Branches.API.FIND_PRODUCT)
public class FindProductServletAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            String key = String.valueOf(body.get(KEY));
            JSONArray array = pool.getArray();
            array.addAll(dao.findProduct(key).stream().map(Product::toJson).collect(Collectors.toList()));
            write(resp, array.toJSONString());
            pool.put(array);
        }
    }
}
