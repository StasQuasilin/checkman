package api.references.products;

import api.ServletAPI;
import constants.Branches;
import entity.DealType;
import entity.products.Product;
import entity.products.ProductAction;
import entity.products.ProductSettings;
import entity.weight.Unit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 03.12.2019.
 */
@WebServlet(Branches.API.PRODUCT_EDIT)
public class EditProductAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null){
            System.out.println(body);
            Product product = dao.getObjectById(Product.class, body.get(ID));
            if (product == null){
                product = new Product();
            }
            boolean save = false;
            String name = String.valueOf(body.get(NAME));
            if (product.getName() == null || !product.getName().equals(name)){
                product.setName(name);
                save = true;
            }

            Unit unit = dao.getObjectById(Unit.class, body.get(UNIT));
            if (unit != null){
                if (product.getUnit() == null || product.getUnit().getId() != unit.getId()){
                    product.setUnit(unit);
                    save = true;
                }
            }

            for(DealType type : DealType.values()){
                checkAction(product, type, (JSONObject)body.get(type.toString()));
            }

            JSONArray array = (JSONArray) body.get(PATH);
            String[] strings = new String[array.size()];
            int i = 0;
            for (Object o : array){
                strings[i++] = String.valueOf(o);
            }
            ProductSettings settings = dao.getProductSettings(product);
            String join = String.join(SLASH, strings);
            if (settings.getPath() == null || !settings.getPath().equals(join)){
                settings.setPath(join);
                dao.save(settings);
            }

            if (save){
                dao.save(product);
            }
            JSONObject json = new SuccessAnswer(RESULT, product.toJson()).toJson();

            write(resp, json.toJSONString());
            pool.put(json);
        }
    }

    private boolean checkContain(JSONObject json, DealType type) {
        return json.containsKey(type.toString()) && Boolean.parseBoolean(String.valueOf(json.get(type.toString())));
    }

    private void checkAction(Product product, DealType type, JSONObject contain) {
        ProductAction action = dao.getProductAction(product, type);
        boolean value = Boolean.parseBoolean(String.valueOf(contain.get(VALUE)));
        if (value){
            if (action == null){
                action = new ProductAction();
                action.setProduct(product);
                action.setType(type);
            }
            boolean editable = Boolean.parseBoolean(String.valueOf(contain.get(EDITABLE)));
            action.setEditable(editable);
            dao.save(action);
        } else if (action != null){
            dao.remove(action);
        }
    }
}
