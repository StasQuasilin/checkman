package api.references.products;

import api.ServletAPI;
import constants.Branches;
import entity.products.ProductGroup;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 03.12.2019.
 */
@WebServlet(Branches.API.References.PRODUCT_GROUP_EDIT)
public class SaveProductGroupAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            ProductGroup group = dao.getObjectById(ProductGroup.class, body.get(ID));
            if (group == null){
                group = new ProductGroup();
            }
            String name = String.valueOf(body.get(NAME));
            if (group.getName() == null || !group.getName().equals(name)){
                group.setName(name);
                dao.save(group);
            }
        }
    }
}
