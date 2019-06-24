package api.laboratory.reports;

import api.API;
import api.laboratory.ActNumberService;
import constants.Branches;
import entity.documents.LoadPlan;
import entity.laboratory.SunAnalyses;
import entity.laboratory.transportation.ActType;
import org.json.simple.JSONObject;
import utils.TransportUtil;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by szpt_user045 on 07.06.2019.
 */
@WebServlet(Branches.API.LABORATORY_SUN_PRINT)
public class LaboratorySunPrintAPI extends API {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long id = -1;
            if (body.containsKey("id")){
                id = (long) body.get("id");
            }
            if(id != -1){
                LoadPlan plan = dao.getLoadPlanById(id);
                float humidity = 0;
                float soreness = 0;
                final int humidityBasis = TransportUtil.HUMIDITY_BASIS;
                final int sorenessBasis = TransportUtil.SORENESS_BASIS;

                final SunAnalyses a = plan.getTransportation().getSunAnalyses();
                final float h1 = a.getHumidity1();
                final float h2 = a.getHumidity2();
                humidity += (h1 > 0 || h2 > 0 ? (
                        (h1 + h2) / ((h1 > 0 ? 1 : 0) + (h2 > 0 ? 1 : 0))
                ) : 0);
                soreness += a.getSoreness();

                float mean = (h1 + h2) / ((h1 > 0 ? 1 : 0) + (h2 > 0 ? 1 : 0));
                if (mean > humidityBasis || soreness > sorenessBasis){
                    if (a.getAct() < 1) {
                        a.setAct(ActNumberService.getActNumber(ActType.sun));
                        dao.save(a);
                    }
                    req.setAttribute("number", a.getAct());
                }

                float percentage = 0;

                if (humidity > humidityBasis && soreness > sorenessBasis){
                    percentage = 100 - ((100-humidity)*(100-soreness)*100)/((100-humidityBasis)*(100-sorenessBasis));
                } else if (humidity > humidityBasis){
                    percentage = ((humidity - humidityBasis) * 100) / (100 - humidityBasis);
                } else if (soreness > sorenessBasis){
                    percentage = ((soreness - sorenessBasis) * 100 / (100 - sorenessBasis));
                }

                req.setAttribute("humidity", humidity);
                req.setAttribute("soreness", soreness);
                req.setAttribute("percent", percentage);
                req.setAttribute("humidityBasis", humidityBasis);
                req.setAttribute("sorenessBasis", sorenessBasis);
                req.setAttribute("now", Date.valueOf(LocalDate.now()));

                req.setAttribute("plan", plan);
                req.getRequestDispatcher("/pages/laboratory/reports/sunReport.jsp").forward(req, resp);
            }
        }
    }
}
