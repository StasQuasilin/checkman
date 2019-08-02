package api.laboratory.reports;

import api.ServletAPI;
import constants.Branches;
import entity.documents.LoadPlan;
import entity.products.Product;
import entity.products.ProductProperty;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 14.06.2019.
 */
@WebServlet(Branches.API.LABORATORY_OIL_PRINT)
public class LaboratoryOilPrintServletAPI extends ServletAPI {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long id = -1;
            if (body.containsKey("id")) {
                id = (long) body.get("id");
            }
            if (id != -1) {
                LoadPlan plan = dao.getLoadPlanById(id);
                Product product = plan.getDeal().getProduct();
                final HashMap<String, String> properties = new HashMap<>();
                for (ProductProperty property : dao.getProductProperties(product)){
                    properties.put(property.getKey(), property.getValue());
                }

                req.setAttribute("plan", plan);
                req.setAttribute("properties", properties);
                req.setAttribute("analyses", plan.getTransportation().getOilAnalyses());
                req.setAttribute("weight", plan.getTransportation().getWeight());
                req.getRequestDispatcher("/pages/laboratory/reports/oilReport.jsp").forward(req, resp);
            }
        }
    }
}
