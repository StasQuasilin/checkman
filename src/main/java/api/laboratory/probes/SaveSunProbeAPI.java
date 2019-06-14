package api.laboratory.probes;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.SunAnalyses;
import entity.laboratory.probes.SunProbe;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 01.04.2019.
 */
@WebServlet(Branches.API.PROBE_SUN_SAVE)
public class SaveSunProbeAPI extends API {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        SunProbe probe;
        boolean save = false;
        if (body.containsKey(Constants.ID)){
            long id = (long) body.get(Constants.ID);
            probe = hibernator.get(SunProbe.class, "id", id);
        } else {
            probe = new SunProbe();
            probe.setAnalyses(new SunAnalyses());
        }

        SunAnalyses analyses = probe.getAnalyses();

        float humidity = Float.parseFloat(String.valueOf(body.get("humidity")));
        if (analyses.getHumidity1() != humidity) {
            analyses.setHumidity1(humidity);
            save = true;
        }

        float soreness = Float.parseFloat(String.valueOf(body.get("soreness")));
        if (analyses.getSoreness() != soreness) {
            analyses.setSoreness(soreness);
            save = true;
        }

        float oiliness = Float.parseFloat(String.valueOf(body.get("oiliness")));
        if (analyses.getOiliness() != oiliness) {
            analyses.setOiliness(oiliness);
            save = true;
        }

        float oilImpurity = Float.parseFloat(String.valueOf(body.get("oilImpurity")));
        if (analyses.getOilImpurity() != oilImpurity) {
            analyses.setOilImpurity(oilImpurity);
            save = true;
        }

        float acidValue = Float.parseFloat(String.valueOf(body.get("acidValue")));
        if (analyses.getAcidValue() != acidValue) {
            analyses.setAcidValue(acidValue);
            save = true;
        }

        if (body.containsKey("manager")){
            long managerId = (long) body.get("manager");
            probe.setManager(hibernator.get(Worker.class, "id", managerId));
            save = true;
        }

        if (body.containsKey("organisation")){
            probe.setOrganisation((String) body.get("organisation"));
            save = true;
        }

        if (save){
            ActionTime createTime = analyses.getCreateTime();
            if (createTime == null) {
                createTime = new ActionTime();
                analyses.setCreateTime(createTime);
            }
            createTime.setTime(new Timestamp(System.currentTimeMillis()));

            Worker worker = getWorker(req);
            if (body.containsKey(Constants.CREATOR)){
                long creatorId = (long) body.get(Constants.CREATOR);
                createTime.setCreator(hibernator.get(Worker.class, "id", creatorId));
            } else {
                createTime.setCreator(worker);
            }
            analyses.setCreator(worker);

            hibernator.save(createTime, analyses, probe);
        }

        PostUtil.write(resp, answer);

    }
}
