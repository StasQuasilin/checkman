package api.laboratory.probes;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.OilAnalyses;
import entity.laboratory.probes.OilProbe;
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
 * Created by quasilin on 01.04.2019.
 */
@WebServlet(Branches.API.PROBE_OIL_SAVE)
public class SaveOilProbeAPI extends API {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        OilProbe probe;
        boolean save = false;
        if (body.containsKey(Constants.ID)){
            long id = (long) body.get(Constants.ID);
            probe = hibernator.get(OilProbe.class, "id", id);
        } else {
            probe = new OilProbe();
            probe.setAnalyses(new OilAnalyses());
        }

        OilAnalyses analyses = probe.getAnalyses();

        boolean organoleptic = (boolean) body.get(Constants.Oil.ORGANOLEPTIC);
        if (analyses.isOrganoleptic() != organoleptic){
            analyses.setOrganoleptic(organoleptic);
            save = true;
        }

        int color = Integer.parseInt(String.valueOf(body.get(Constants.Oil.COLOR)));
        if (analyses.getColor() != color) {
            analyses.setColor(color);
            save = true;
        }

        float acid = Float.parseFloat(String.valueOf(body.get(Constants.Oil.ACID_VALUE)));
        if (analyses.getAcidValue() != acid) {
            analyses.setAcidValue(acid);
            save = true;
        }

        float peroxide = Float.parseFloat(String.valueOf(body.get(Constants.Oil.PEROXIDE_VALUE)));
        if (analyses.getPeroxideValue() != peroxide) {
            analyses.setPeroxideValue(peroxide);
            save = true;
        }

        float phosphorus = Float.parseFloat(String.valueOf(body.get(Constants.Oil.PHOSPHORUS)));
        if (analyses.getPhosphorus() != phosphorus) {
            analyses.setPhosphorus(phosphorus);
            save = true;
        }

        float humidity = Float.parseFloat(String.valueOf(body.get(Constants.Sun.HUMIDITY_1)));
        if (analyses.getHumidity() != humidity) {
            analyses.setHumidity(humidity);
            save = true;
        }

        boolean soap = Boolean.parseBoolean(String.valueOf(body.get(Constants.Oil.SOAP)));
        if (analyses.isSoap() != soap) {
            analyses.setSoap(soap);
            save = true;
        }

        float wax = Float.parseFloat(String.valueOf(body.get(Constants.Oil.WAX)));
        if (analyses.getWax() != wax) {
            analyses.setWax(wax);
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
