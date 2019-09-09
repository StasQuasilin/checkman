package api.laboratory.reports;

import api.ServletAPI;
import api.laboratory.ActNumberService;
import constants.Branches;
import entity.AnalysesType;
import entity.Worker;
import entity.documents.LoadPlan;
import entity.laboratory.transportation.ActType;
import entity.products.Product;
import entity.products.ProductProperty;
import entity.transport.Transportation;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
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
                id = Long.parseLong(String.valueOf(body.get("id")));
            }

            if (id != -1) {
                int number = Integer.parseInt(String.valueOf(body.get("number")));
                Object dateObj = body.get("date");
                Date date = dateObj != null ? Date.valueOf(String.valueOf(dateObj)) : Date.valueOf(LocalDate.now());
                Object manufactureObj = body.get("manufacture");
                Date manufacture = manufactureObj != null ? Date.valueOf(String.valueOf(manufactureObj)) : Date.valueOf(LocalDate.now());
                Worker responsible = dao.getWorkerById(body.get("worker"));
                AnalysesType type = AnalysesType.valueOf(String.valueOf(body.get("type")));


                Transportation transportation = dao.getTransportationById(id);
                Product product = transportation.getProduct();
                final HashMap<String, String> properties = new HashMap<>();
                for (ProductProperty property : dao.getProductProperties(product)){
                    properties.put(property.getKey(), property.getValue());
                }
                switch (type){
                    case sun:
                        req.setAttribute("analyses", transportation.getSunAnalyses());
                        break;
                    case oil:
                        req.setAttribute("analyses", transportation.getOilAnalyses());
                        break;
                    case meal:
                        req.setAttribute("analyses", transportation.getMealAnalyses());
                        break;
                }
                req.setAttribute("number", number);
                req.setAttribute("date", date);
                req.setAttribute("manufacture", manufacture);
                req.setAttribute("responsible", responsible);
                req.setAttribute("plan", transportation);
                req.setAttribute("properties", properties);
                req.setAttribute("analyses", transportation.getOilAnalyses());
                req.setAttribute("weight", transportation.getWeight());
                req.getRequestDispatcher("/pages/laboratory/reports/oilReport.jsp").forward(req, resp);


                ActNumberService.updateNumber(ActType.valueOf(type.name() + "Act"), number);
            }
        }
    }
}
