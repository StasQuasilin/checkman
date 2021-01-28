package api.references.products;

import api.ServletAPI;
import constants.Branches;
import entity.products.ProductGroup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 03.12.2019.
 */
@WebServlet(Branches.API.References.FIND_PRODUCT_GROUP)
public class FindProductGroupAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            String key = String.valueOf(body.get(KEY));
            JSONArray array = pool.getArray();
            array.addAll(dao.find(ProductGroup.class, NAME, key).stream().map(ProductGroup::toJson).collect(Collectors.toList()));
            write(resp, array.toJSONString());
            pool.put(array);
        }
    }
}
