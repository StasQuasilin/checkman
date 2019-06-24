package api.laboratory.vro.reports;

import api.API;
import constants.Branches;
import entity.laboratory.LaboratoryTurn;
import entity.laboratory.subdivisions.vro.VROTurn;
import entity.production.Forpress;
import entity.production.TurnSettings;
import org.json.simple.JSONObject;
import utils.turns.LaboratoryTurnService;
import utils.turns.TurnBox;
import utils.turns.VROTurnService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by szpt_user045 on 03.06.2019.
 */
@WebServlet(Branches.API.VRO_DAILY_REPORT_PRINT)
public class VRODailyReportPrintAPI extends API {

    final TurnBox turnBox = TurnBox.getBox();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            JSONObject parameters = (JSONObject) body.get("parameters");
            final LocalDate date = LocalDate.parse(String.valueOf(parameters.get("date")));
            final List<VROTurn> turns = new LinkedList<>();
            final HashMap<Integer, List<LaboratoryTurn>> laboratoryTurns = new HashMap<>();
            for (TurnSettings turn : turnBox.getTurns()) {
                LocalTime time = turn.getBegin().toLocalTime();
                LocalDateTime dateTime = LocalDateTime.of(date, time);
                VROTurn vroTurn = VROTurnService.getTurn(turnBox.getTurnDate(dateTime));
                if (vroTurn.getCrudes() != null) {
                    Collections.sort(vroTurn.getCrudes());
                }
                turns.add(vroTurn);
                laboratoryTurns.put(vroTurn.getTurn().getId(), LaboratoryTurnService.getTurns(vroTurn.getTurn()));
            }

            req.setAttribute("laboratory", laboratoryTurns);
            req.setAttribute("forpress", dao.getForpressList());
            req.setAttribute("turns", turns);
            req.getRequestDispatcher("/pages/laboratory/subdivisions/vro/reports/print/dailyReport.jsp").forward(req, resp);
        } else {
            write(resp, emptyBody);
        }
    }
}