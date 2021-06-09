package utils.laboratory;

import entity.Worker;
import entity.laboratory.SunAnalyses;
import entity.transport.ActionTime;
import entity.transport.TransportationProduct;
import utils.hibernate.dao.TransportationDAO;
import utils.json.JsonObject;

import static constants.Constants.*;

public class SunAnalysesEditor extends AnalysesEditor {

    @Override
    public boolean editAnalyses(TransportationProduct transportationProduct, JsonObject json, Worker worker) {

        System.out.println(json);

        if(json != null) {
            boolean save = false;
            boolean update = false;
            SunAnalyses sunAnalyses = transportationProduct.getSunAnalyses();
            if (sunAnalyses == null) {
                sunAnalyses = new SunAnalyses();
                update = true;
            }

            final float soreness = json.getFloat(SORENESS);
            if (changeIt(soreness, sunAnalyses.getSoreness())) {
                sunAnalyses.setSoreness(soreness);
                save = true;
            }

            final float humidity1 = json.getFloat(HUMIDITY1);
            if (changeIt(humidity1, sunAnalyses.getHumidity1())) {
                sunAnalyses.setHumidity1(humidity1);
                save = true;
            }

            final float humidity2 = json.getFloat(HUMIDITY2);
            if (changeIt(humidity2, sunAnalyses.getHumidity2())) {
                sunAnalyses.setHumidity2(humidity2);
                save = true;
            }

            final float oiliness = json.getFloat(OILINESS);
            if (changeIt(oiliness, sunAnalyses.getOiliness())) {
                sunAnalyses.setOiliness(oiliness);
                save = true;
            }

            final float oilImpurity = json.getFloat(OIL_IMPURITY);
            if (changeIt(oilImpurity, sunAnalyses.getOilImpurity())) {
                sunAnalyses.setOilImpurity(oilImpurity);
                save = true;
            }

            final float acidValue = json.getFloat(ACID_VALUE);
            if (changeIt(acidValue, sunAnalyses.getAcidValue())) {
                sunAnalyses.setAcidValue(acidValue);
                save = true;
            }

            final boolean contamination = json.getBool(CONTAMINATION);
            if (contamination != sunAnalyses.isContamination()) {
                sunAnalyses.setContamination(contamination);
                save = true;
            }

            if (save) {
                ActionTime createTime = sunAnalyses.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime(worker);
                    save(createTime);
                    sunAnalyses.setCreateTime(createTime);
                }
                save(sunAnalyses);
                if (update){
                    transportationProduct.setSunAnalyses(sunAnalyses);
                    transportationDAO.saveProduct(transportationProduct);
                }
            }
            return save;
        }

        return false;
    }

    private void save(SunAnalyses sunAnalyses) {
        dao.save(sunAnalyses);
    }

    private boolean changeIt(float s1, float s2) {
        return Math.abs(s1 - s2) > 0.0001;
    }
}
