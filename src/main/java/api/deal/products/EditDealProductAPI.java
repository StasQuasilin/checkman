package api.deal.products;

import api.IChangeAPI;
import constants.Branches;
import constants.Constants;
import entity.products.Product;
import entity.documents.Deal;
import entity.documents.DealProduct;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.Deal.EDIT_PRODUCT)
public class EditDealProductAPI extends IChangeAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);

        DealProduct dealProduct;
        boolean write = false;
        try {
            int id = Integer.parseInt(body.get(Constants.ID));
            dealProduct = dao.getDealProductById(id);
        } catch (Exception ignored){
            dealProduct = new DealProduct();
            dealProduct.setCreator(getWorker(req));
            dealProduct.setDeal(dao.getDealById(Integer.parseInt(body.get(Constants.DEAL_ID))));
            write = true;
        }

        Product product = dao.getProductById(Integer.parseInt(body.get(Constants.PRODUCT_ID)));

        if (dealProduct.getProduct() == null || dealProduct.getProduct().getId() != product.getId()){
            dealProduct.setProduct(product);
            write = true;
        }

        float quantity = Float.parseFloat(body.get(Constants.QUANTITY));
        if (dealProduct.getQuantity() != quantity){
            dealProduct.setQuantity(quantity);
            write = true;
        }

        float price = Float.parseFloat(body.get(Constants.PRICE));
        if (dealProduct.getPrice() != price){
            dealProduct.setPrice(price);
            write = true;
        }

        if (write){
            dao.save(dealProduct);
        }

        body.clear();
    }
}
