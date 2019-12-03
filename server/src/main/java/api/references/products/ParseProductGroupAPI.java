package api.references.products;

import api.ServletAPI;
import constants.Branches;
import entity.products.ProductGroup;
import org.json.simple.JSONObject;
import utils.answers.SuccessAnswer;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 03.12.2019.
 */
@WebServlet(Branches.API.References.PARSE_PRODUCT_GROUP)
public class ParseProductGroupAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            String name = String.valueOf(body.get(KEY));
            ProductGroup group = new ProductGroup();
            group.setName(name);
            dao.save(group);
            JSONObject json = new SuccessAnswer(RESULT, group.toJson()).toJson();
            write(resp, json.toJSONString());
            pool.put(json);
        }
    }
}
