package api.retail;

import api.ServletAPI;
import constants.Branches;
import entity.deal.Contract;
import entity.deal.ContractProduct;
import entity.documents.Shipper;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by szpt_user045 on 11.12.2019.
 */
@WebServlet(Branches.API.RETAIL_PRINT)
public class RetailPrintAPI extends ServletAPI{

    public static final String PAGE = "/pages/retail/retailPrint.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null){
            Contract contract = dao.getObjectById(Contract.class, body.get(CONTRACT));
            Shipper shipper = dao.getShipperById(1);

            ArrayList<ContractProduct> products = new ArrayList<>();
            for (ContractProduct contractProduct : contract.getProducts()){
                if (contractProduct.getShipper().getId() != shipper.getId()){
                    products.add(contractProduct);
                }
            }
            contract.getProducts().clear();

            req.setAttribute(CONTRACT, contract);
            req.setAttribute(PRODUCTS, products);
            req.getRequestDispatcher(PAGE).forward(req, resp);
        }
    }
}
