package api.references.products;

import api.ServletAPI;
import constants.Branches;
import entity.products.Product;
import org.json.simple.JSONObject;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 29.11.2019.
 */
@WebServlet(Branches.API.PARSE_PRODUCT)
public class ParseProductServletAPI extends ServletAPI{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            String key = String.valueOf(body.get(KEY));
            Product product = dao.getProductByName(key);
            boolean isNew = false;
            if (product == null){
                product = new Product();
                product.setName(key);
                dao.save(product);
                isNew = true;
            }
            JSONObject p = product.toJson();
            p.put(IS_NEW, isNew);
            JSONObject json = new SuccessAnswer(RESULT, p).toJson();

            write(resp, json.toJSONString());
            pool.put(json);
        }
    }
}
