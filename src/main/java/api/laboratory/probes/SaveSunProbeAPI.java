package api.laboratory.probes;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.SunAnalyses;
import entity.laboratory.probes.SunProbe;
import entity.organisations.Organisation;
import entity.production.Turn;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;
import utils.TurnDateTime;
import utils.U;
import utils.turns.TurnBox;
import utils.turns.TurnService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by szpt_user045 on 01.04.2019.
 */
@WebServlet(Branches.API.PROBE_SUN_SAVE)
public class SaveSunProbeAPI extends API {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            SunProbe probe;

            boolean save = false;
            if (body.containsKey(Constants.ID)) {
                Object id = body.get(Constants.ID);
                probe = dao.getSunProbeById(id);
            } else {
                probe = new SunProbe();
                final TurnDateTime turnDate = TurnBox.getBox().getTurnDate(LocalDateTime.now());
                final Turn turn = TurnService.getTurn(turnDate);
                probe.setTurn(turn);
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
            JSONObject m = (JSONObject) body.get("manager");
            Worker manager = null;
            String managerValue = null;
            if (m != null){
                manager = dao.getWorkerById(m.get(Constants.ID));
                managerValue = String.valueOf(m.get("value"));
            }

            if (manager != null){
                managerValue = manager.getValue();
            }

            if(U.exist(managerValue) && !managerValue.equals(probe.getManager())) {
                probe.setManager(managerValue);
                save = true;
            }

            JSONObject o = (JSONObject) body.get("organisation");
            Organisation organisation = null;
            String organisationValue = null;
            if (o != null) {
                organisation = dao.getOrganisationById(o.get("id"));
                organisationValue = String.valueOf(o.get("value"));
            }

            if (organisation != null){
                organisationValue = organisation.getValue();
            }

            if (U.exist(organisationValue) && !organisationValue.equals(probe.getOrganisation())) {
                probe.setOrganisation(organisationValue);
                save = true;
            }

            if (save) {
                ActionTime createTime = analyses.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    analyses.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));

                Worker worker = getWorker(req);
                if (body.containsKey(Constants.CREATOR)) {
                    Object creatorId = body.get(Constants.CREATOR);
                    createTime.setCreator(dao.getWorkerById(creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                analyses.setCreator(worker);

                dao.save(createTime);
                dao.save(analyses);
                dao.save(probe);
            }

            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }

    }
}
