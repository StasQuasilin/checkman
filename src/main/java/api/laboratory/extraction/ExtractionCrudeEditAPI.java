package api.laboratory.extraction;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.laboratory.subdivisions.extraction.ExtractionCrude;
import org.json.simple.JSONObject;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 04.04.2019.
 */
@WebServlet(Branches.API.EXTRACTION_CRUDE_EDIT)
public class ExtractionCrudeEditAPI extends IAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        ExtractionCrude crude;
        if (body.containsKey(Constants.ID)){
            long id = (long) body.get(Constants.ID);
            crude = hibernator.get(ExtractionCrude.class, "id", id);
        } else {
            crude = new ExtractionCrude();
        }

        boolean save = false;
        float humidityIncome = Float.parseFloat(String.valueOf(body.get("humidityIncome")));
        if (crude.getHumidityIncome() != humidityIncome) {
            crude.setHumidityIncome(humidityIncome);
            save = true;
        }

        float fraction = Float.parseFloat(String.valueOf(body.get("fraction")));
        if (crude.getFraction() != fraction) {
            crude.setFraction(fraction);
            save = true;
        }

        float miscellas = Float.parseFloat(String.valueOf(body.get("miscellas")));
        if (crude.getMiscellas() != miscellas) {
            crude.setMiscellas(miscellas);
            save = true;
        }

        float humidity = Float.parseFloat(String.valueOf(body.get("humidity")));
        if (crude.getHumidity() != humidity){
            crude.setHumidity(humidity);
            save = true;
        }

        float dissolvent = Float.parseFloat(String.valueOf(body.get("dissolvent")));
        if (crude.getDissolvent() != dissolvent) {
            crude.setDissolvent(dissolvent);
            save = true;
        }

        float grease = Float.parseFloat(String.valueOf(body.get("grease")));
        if (crude.getGrease() != grease) {
            crude.setGrease(grease);
            save = true;
        }

        if (save) {
            hibernator.save(crude);
        }
    }
}
