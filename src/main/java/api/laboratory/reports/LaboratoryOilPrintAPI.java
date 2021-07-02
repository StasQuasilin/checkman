package api.laboratory.reports;

import api.ServletAPI;
import api.laboratory.ActNumberService;
import constants.Branches;
import entity.AnalysesType;
import entity.Worker;
import entity.laboratory.OilAnalyses;
import entity.laboratory.Protocol;
import entity.laboratory.SunAnalyses;
import entity.laboratory.transportation.ActType;
import entity.products.Product;
import entity.products.ProductProperty;
import entity.transport.TransportUtil;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;
import entity.weight.Weight;
import org.json.simple.JSONObject;
import utils.hibernate.dao.TransportationDAO;

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
public class LaboratoryOilPrintAPI extends ServletAPI {

    private final TransportationDAO transportationDAO = new TransportationDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            System.out.println(body);
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
                Worker responsible = dao.getObjectById(Worker.class, body.get("worker"));
                AnalysesType type = AnalysesType.valueOf(String.valueOf(body.get("type")));

                final TransportationProduct transportationProduct = transportationDAO.getTransportationProduct(id);
                Product product = transportationProduct.getDealProduct().getProduct();
                final HashMap<String, String> properties = new HashMap<>();
                for (ProductProperty property : dao.getProductProperties(product)){
                    properties.put(property.getKey(), property.getValue());
                }
                req.setAttribute(NUMBER, number);
                req.setAttribute(DATE, date);
                req.setAttribute("manufacture", manufacture);
                req.setAttribute("responsible", responsible);
                req.setAttribute(PRODUCT, transportationProduct);
                req.setAttribute("properties", properties);
//                req.setAttribute("analyses", transportation.getOilAnalyses());
                req.setAttribute("weight", transportationProduct.getWeight());

                Protocol protocol = dao.getProtocol(product);
                req.setAttribute(PROTOCOL, protocol);
                req.setAttribute(CONTEXT, req.getContextPath());

                switch (type){
                    case sun:
                        final SunAnalyses sunAnalyses = transportationProduct.getSunAnalyses();
                        TransportUtil.calculateWeight(transportationProduct);
                        req.setAttribute(ANALYSES, sunAnalyses);
                        req.setAttribute("humidityBasis", 7);
                        req.setAttribute("sorenessBasis", 3);
                        req.getRequestDispatcher("/pages/laboratory/reports/sunReport.jsp").forward(req, resp);
                        if (sunAnalyses.getAct() != number){
                            sunAnalyses.setAct(number);
                            dao.save(transportationProduct);
                        }
                        break;
                    case oil:
                        final OilAnalyses oilAnalyses = transportationProduct.getOilAnalyses();
                        req.setAttribute(ANALYSES, oilAnalyses);
                        req.getRequestDispatcher("/pages/laboratory/reports/oilReport.jsp").forward(req, resp);
                        if (oilAnalyses.getAct() != number){
                            oilAnalyses.setAct(number);
                            dao.save(transportationProduct);
                        }
                        break;
                    case meal:
//                        req.setAttribute("analyses", transportation.getMealAnalyses());
                        req.getRequestDispatcher("/pages/laboratory/reports/mealReport.jsp").forward(req, resp);
//                        if (transportation.getMealAnalyses().getAct() != number){
//                            transportation.getMealAnalyses().setAct(number);
//                            dao.save(transportation);
//                        }
                        break;
                }

                ActNumberService.updateNumber(ActType.valueOf(type.name() + "Act"), number);
            }
        }
    }
}
