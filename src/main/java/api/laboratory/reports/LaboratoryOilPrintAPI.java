package api.laboratory.reports;

import api.API;
import constants.Branches;
import entity.documents.LoadPlan;
import entity.laboratory.OilAnalyses;
import entity.laboratory.transportation.CakeTransportationAnalyses;
import entity.laboratory.transportation.OilTransportationAnalyses;
import entity.products.Product;
import entity.products.ProductProperty;
import entity.weight.Weight;
import org.json.simple.JSONObject;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by szpt_user045 on 14.06.2019.
 */
@WebServlet(Branches.API.LABORATORY_OIL_PRINT)
public class LaboratoryOilPrintAPI extends API {

    dbDAO dao = dbDAOService.getDAO();

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
