package api.references.products;

import api.ServletAPI;
import constants.Branches;
import entity.products.Product;
import entity.products.ProductSettings;
import org.json.simple.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.References.PRODUCT_LIST)
public class ProductListAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONArray array = pool.getArray();

        for(Product product : dao.getObjects(Product.class)){
            ProductSettings productSettings = dao.getProductSettings(product);
            if (productSettings == null){
                productSettings = new ProductSettings();
                productSettings.setProduct(product);
            }
            array.add(productSettings.toJson());
        }

        write(resp, array.toJSONString());
        pool.put(array);
    }
}
