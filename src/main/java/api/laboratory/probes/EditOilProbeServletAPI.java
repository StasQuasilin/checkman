package api.laboratory.probes;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.OilAnalyses;
import entity.laboratory.probes.OilProbe;
import entity.laboratory.probes.ProbeTurn;
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
 * Created by quasilin on 01.04.2019.
 */
@WebServlet(Branches.API.PROBE_OIL_SAVE)
public class EditOilProbeServletAPI extends ServletAPI {

    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            OilProbe probe;
            boolean save = false;

            final TurnDateTime turnDate = TurnBox.getTurnDate(LocalDateTime.now());
            final ProbeTurn target = TurnService.getProbeTurn(turnDate);


            if (body.containsKey(Constants.ID)) {
                probe = dao.getOilProbeById(body.get(Constants.ID));
            } else {
                probe = new OilProbe();
                probe.setAnalyses(new OilAnalyses());
                probe.setTurn(target);
                probe.setDate(Date.valueOf(LocalDate.now()));
            }

            OilAnalyses analyses = probe.getAnalyses();

            boolean organoleptic = (boolean) body.get(Constants.Oil.ORGANOLEPTIC);
            if (analyses.isOrganoleptic() != organoleptic) {
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

            float humidity = Float.parseFloat(String.valueOf(body.get(HUMIDITY)));
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
                    createTime.setCreator(dao.getObjectById(Worker.class, body.get(Constants.CREATOR)));
                } else {
                    createTime.setCreator(worker);
                }

                dao.save(createTime);
                dao.save(analyses);
                dao.save(probe);
                updateUtil.onSave(dao.getProbeTurnByTurn(target.getTurn()));
            }

            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
