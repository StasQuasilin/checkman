package api.manufacture;

import api.ServletAPI;
import constants.Branches;
import entity.laboratory.subdivisions.extraction.ExtractionCrude;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.laboratory.subdivisions.vro.VROCrude;
import entity.laboratory.subdivisions.vro.VRODaily;
import entity.laboratory.subdivisions.vro.VROTurn;
import entity.production.Turn;
import entity.production.TurnSettings;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.DateUtil;
import utils.TurnDateTime;
import utils.storages.PointScale;
import utils.storages.StorageUtil;
import utils.turns.TurnBox;
import utils.turns.TurnService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by szpt_user045 on 05.12.2019.
 */
@WebServlet(Branches.API.MANUFACTURE_ANALYSE)
public class ManufactureAnalyseAPI extends ServletAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            String string = String.valueOf(body.get(MONTH));

            LocalDate from = LocalDate.parse(string);
            LocalDate to = StorageUtil.getEndDate(from, PointScale.month);
            JSONObject json = pool.getObject();
            json.put(FROM, Date.valueOf(from).toString());
            json.put(TO, Date.valueOf(to).toString());

            JSONArray array = pool.getArray();
            json.put(VALUES, array);
            List<TurnSettings> turns = TurnBox.getTurns();
            for (LocalDate d = from.plusDays(0); d.isBefore(to.plusDays(1)); d = d.plusDays(1)){
                for (TurnSettings turnSettings : turns){
                    TurnDateTime turnDate = TurnBox.getTurnDate(LocalDateTime.of(d, turnSettings.getBegin().toLocalTime()));
                    Turn turn = dao.getTurnByDate(turnDate);
                    if (turn != null) {
                        JSONObject object = pool.getObject();

                        object.put(DATE, d.toString());
                        object.put(TURN, turnSettings.getNumber());


                        float humidityBefore = 0;
                        float sorenessBefore = 0;
                        float humidityAfter = 0;
                        float sorenessAfter = 0;
                        float kernelHuskiness = 0;
                        float kernelOffset = 0;
                        float huskOiliness = 0;
                        float huskHumidity = 0;
                        float cakeOiliness = 0;
                        float cakeHumidity = 0;
                        float pulpHumidity = 0;
                        float mealOiliness = 0;
                        float mealHumidity = 0;

                        VROTurn vroTurn = dao.getVROTurnByTurn(turn);

                        if (vroTurn != null) {
                            for (VROCrude crude : vroTurn.getCrudes()) {
                                humidityBefore += crude.getHumidityBefore();
                                sorenessBefore += crude.getSorenessBefore();
                                humidityAfter += crude.getHumidityAfter();
                                sorenessAfter += crude.getSorenessAfter();
                                kernelHuskiness += crude.getHuskiness();
                                kernelOffset += crude.getKernelOffset();
                                float pulpAmount = 0;
                                if (crude.getPulpHumidity1() > 0) {
                                    pulpAmount++;
                                }
                                if (crude.getPulpHumidity2() > 0) {
                                    pulpAmount++;
                                }
                                pulpHumidity += (crude.getPulpHumidity1() + crude.getPulpHumidity1()) / pulpAmount;
                            }
                            int size = vroTurn.getCrudes().size();
                            if (size > 0) {
                                humidityBefore /= size;
                                sorenessBefore /= size;
                                humidityAfter /= size;
                                sorenessAfter /= size;
                                kernelHuskiness /= size;
                                kernelOffset /= size;
                                huskOiliness /= size;
                                huskHumidity /= size;
                                pulpHumidity /= size;
                            }
                            for (VRODaily daily : vroTurn.getDailies()) {
                                huskHumidity += daily.getHuskHumidity();
                            }
                            huskHumidity /= vroTurn.getDailies().size();
                        }

                        ExtractionTurn extractionTurn = dao.getExtractionTurnByTurn(turn);
                        if (extractionTurn != null) {
                            for (ExtractionCrude crude : extractionTurn.getCrudes()) {
                                cakeOiliness += crude.getOilinessIncome();
                                cakeHumidity += crude.getHumidityIncome();
                                mealOiliness += crude.getGrease();
                                mealHumidity += crude.getHumidity();
                            }
                            int size = extractionTurn.getCrudes().size();
                            cakeOiliness /= size;
                            cakeHumidity /= size;
                            mealOiliness /= size;
                            mealHumidity /= size;
                        }

                        object.put("humidityBefore", humidityBefore);
                        object.put("sorenessBefore", sorenessBefore);
                        object.put("humidityAfter", humidityAfter);
                        object.put("sorenessAfter", sorenessAfter);
                        object.put("kernelHuskiness", kernelHuskiness);
                        object.put("kernelOffset", kernelOffset);
                        object.put("huskOiliness", huskOiliness);
                        object.put("huskHumidity", huskHumidity);
                        object.put("cakeOiliness", cakeOiliness);
                        object.put("cakeHumidity", cakeHumidity);
                        object.put("pulpHumidity", pulpHumidity);
                        object.put("mealOiliness", mealOiliness);
                        object.put("mealHumidity", mealHumidity);

                        array.add(object);
                    }
                }
            }
            write(resp, json.toJSONString());
            pool.put(array);
        }
    }
}
