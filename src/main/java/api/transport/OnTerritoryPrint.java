package api.transport;

import api.ServletAPI;
import constants.Branches;
import entity.products.Product;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 01.11.2019.
 */
@WebServlet(Branches.API.ON_TERRITORY_PRINT)
public class OnTerritoryPrint extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<Product, ArrayList<Transportation>> transportations = new HashMap<>();
        for (Transportation transportation : dao.getTransportationsOnTerritory()){
            for (TransportationProduct transportationProduct : transportation.getProducts()){
                final Product product = transportationProduct.getDealProduct().getProduct();
                if (!transportations.containsKey(product)){
                    transportations.put(product, new ArrayList<>());
                }
                transportations.get(product).add(transportation);
            }
        }
        req.setAttribute("transport", transportations);
        req.setAttribute("now", Timestamp.valueOf(LocalDateTime.now()));
        req.getRequestDispatcher("/pages/transport/print/onTerritory.jsp").forward(req, resp);
    }
}
