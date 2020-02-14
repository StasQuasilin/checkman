package api.deal.products;

import api.IChangeServletAPI;
import constants.Branches;
import constants.Constants;
import entity.products.Product;
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
public class EditDealProductServletAPI extends IChangeServletAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
