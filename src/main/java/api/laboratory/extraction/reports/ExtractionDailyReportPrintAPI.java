package api.laboratory.extraction.reports;

import api.IAPI;
import constants.Branches;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.production.Turn;
import org.json.simple.JSONObject;
import utils.turns.ExtractionTurnService;
import utils.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
            LocalDate date = LocalDate.parse(String.valueOf(parameters.get("date")));
            List<ExtractionTurn> turns = new LinkedList<>();
            for (Turn turn : turnBox.getTurns()) {
                LocalTime time = turn.getBegin().toLocalTime();
                LocalDateTime dateTime = LocalDateTime.of(
                        date.getYear(),
                        date.getMonth(),
                        date.getDayOfMonth(),
                        time.getHour(),
                        time.getMinute());
                turns.add(ExtractionTurnService.getTurn(turnBox.getTurnDate(dateTime)));
            }
            req.setAttribute("turns", turns);
            req.getRequestDispatcher("/pages/laboratory/subdivisions/extraction/reports/print/dailyReport.jsp").forward(req, resp);
        } else {
            write(resp, emptyBody);
        }
    }
}
