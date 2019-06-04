package api.laboratory.extraction.reports;

import api.IAPI;
import constants.Branches;
import entity.laboratory.LaboratoryTurn;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.production.TurnSettings;
import org.json.simple.JSONObject;
import utils.turns.ExtractionTurnService;
import utils.turns.LaboratoryTurnService;
import utils.turns.TurnBox;

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
 * Created by szpt_user045 on 17.05.2019.
 */
@WebServlet(Branches.API.EXTRACTION_DAILY_REPORT_PRINT)
public class ExtractionDailyReportPrintAPI extends IAPI{

    final TurnBox turnBox = TurnBox.getBox();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            JSONObject parameters = (JSONObject) body.get("parameters");
            final LocalDate date = LocalDate.parse(String.valueOf(parameters.get("date")));
            final List<ExtractionTurn> turns = new LinkedList<>();
            final HashMap<Integer, List<LaboratoryTurn>> laboratoryTurns = new HashMap<>();
            for (TurnSettings turn : turnBox.getTurns()) {
                LocalTime time = turn.getBegin().toLocalTime();
                LocalDateTime dateTime = LocalDateTime.of(date, time);
                ExtractionTurn extractionTurn = ExtractionTurnService.getTurn(turnBox.getTurnDate(dateTime));
                if (extractionTurn.getCrudes() != null) {
                    Collections.sort(extractionTurn.getCrudes());
                }
                turns.add(extractionTurn);
                laboratoryTurns.put(extractionTurn.getTurn().getId(), LaboratoryTurnService.getTurns(extractionTurn.getTurn()));
            }
            req.setAttribute("laboratory", laboratoryTurns);
            req.setAttribute("turns", turns);
            req.getRequestDispatcher("/pages/laboratory/subdivisions/extraction/reports/print/dailyReport.jsp").forward(req, resp);
        } else {
            write(resp, emptyBody);
        }
    }
}
