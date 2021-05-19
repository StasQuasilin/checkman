package api.laboratory.probes;

import api.ServletAPI;
import bot.TelegramBotFactory;
import bot.TelegramNotificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.SunAnalyses;
import entity.laboratory.probes.ProbeTurn;
import entity.laboratory.probes.SunProbe;
import entity.organisations.Organisation;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;
import utils.TurnDateTime;
import utils.U;
import utils.UpdateUtil;
import utils.turns.TurnBox;
import utils.turns.TurnService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by szpt_user045 on 01.04.2019.
 */
@WebServlet(Branches.API.PROBE_SUN_SAVE)
public class EditSunProbeAPI extends ServletAPI {

    final UpdateUtil updateUtil = new UpdateUtil();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            SunProbe probe;

            boolean save = false;
            final TurnDateTime turnDate = TurnBox.getTurnDate(LocalDateTime.now());
            ProbeTurn targetTurn = TurnService.getProbeTurn(turnDate);

            if (body.containsKey(Constants.ID)) {
                probe = dao.getSunProbeById(body.get(Constants.ID));
            } else {
                probe = new SunProbe();
                probe.setAnalyses(new SunAnalyses());
                probe.setTurn(targetTurn);
                probe.setDate(Date.valueOf(LocalDate.now()));
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
                manager = dao.getObjectById(Worker.class, m.get(Constants.ID));
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
                    createTime.setCreator(dao.getObjectById(Worker.class, creatorId));
                } else {
                    createTime.setCreator(worker);
                }

                dao.save(createTime);
                dao.save(analyses);
                dao.save(probe);
                updateUtil.onSave(dao.getProbeTurnByTurn(targetTurn.getTurn()));
                TelegramNotificator notificator = TelegramBotFactory.getTelegramNotificator();
                if (notificator != null){
                    notificator.sunProbe(probe);
                }
            }

            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, EMPTY_BODY);
        }

    }
}
