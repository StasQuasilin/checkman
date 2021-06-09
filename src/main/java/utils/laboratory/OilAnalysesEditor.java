package utils.laboratory;

import entity.Worker;
import entity.laboratory.OilAnalyses;
import entity.transport.ActionTime;
import entity.transport.TransportationProduct;
import utils.json.JsonObject;

import static constants.Constants.*;

public class OilAnalysesEditor extends AnalysesEditor<OilAnalyses> {

//    "oil": {
//                "benzopyrene": 0,
//    },

    @Override
    boolean parse(TransportationProduct t, JsonObject json, Worker worker) {
        if (json != null){
            boolean save = false;
            boolean update = false;
            OilAnalyses oilAnalyses = t.getOilAnalyses();
            if (oilAnalyses == null){
                oilAnalyses = new OilAnalyses();
                update = true;
            }

            final boolean organoleptic = json.getBool(ORGANOLEPTIC);
            if (organoleptic != oilAnalyses.isOrganoleptic()){
                oilAnalyses.setOrganoleptic(organoleptic);
                save = true;
            }

            final float color = json.getFloat(COLOR);
            if (changeIt(color, oilAnalyses.getColor())){
                oilAnalyses.setColor(color);
                save = true;
            }

            final float degreaseImpurity = json.getFloat(DEGREASE_IMPURITY);
            if (changeIt(degreaseImpurity, oilAnalyses.getDegreaseImpurity())){
                oilAnalyses.setDegreaseImpurity(degreaseImpurity);
                save = true;
            }

            final float peroxideValue = json.getFloat(PEROXIDE_VALUE);
            if(changeIt(peroxideValue, oilAnalyses.getPeroxideValue())){
                oilAnalyses.setPeroxideValue(peroxideValue);
                save = true;
            }

            final float humidity = json.getFloat(HUMIDITY);
            if (changeIt(humidity, oilAnalyses.getHumidity())){
                oilAnalyses.setHumidity(humidity);
                save = true;
            }

            final float acidValue = json.getFloat(ACID_VALUE);
            if(changeIt(acidValue, oilAnalyses.getAcidValue())){
                oilAnalyses.setAcidValue(acidValue);
                save = true;
            }

            final float explosion = json.getFloat(EXPLOSION);
            if (changeIt(explosion, oilAnalyses.getExplosion())){
                oilAnalyses.setExplosion(explosion);
                save = true;
            }

            final float transparency = json.getFloat(TRANSPARENCY);
            if (changeIt(transparency, oilAnalyses.getTransparency())){
                oilAnalyses.setTransparency(transparency);
                save = true;
            }

            final float phosphorus = json.getFloat(PHOSPHORUS);
            if (changeIt(phosphorus, oilAnalyses.getPhosphorus())){
                oilAnalyses.setPhosphorus(phosphorus);
                save = true;
            }

            final float benzopyrene = json.getFloat(BENZOPYRENE);
            if(changeIt(benzopyrene, oilAnalyses.getBenzopyrene())){
                oilAnalyses.setBenzopyrene(benzopyrene);
                save = true;
            }

            if (save){
                ActionTime createTime = oilAnalyses.getCreateTime();
                if (createTime == null){
                    createTime = new ActionTime(worker);
                    save(createTime);
                    oilAnalyses.setCreateTime(createTime);
                }
                save(oilAnalyses);
                if(update){
                    t.setOilAnalyses(oilAnalyses);
                }
            }
            return update;
        }

        return false;
    }

    private void save(OilAnalyses oilAnalyses) {
        dao.save(oilAnalyses);
    }
}
