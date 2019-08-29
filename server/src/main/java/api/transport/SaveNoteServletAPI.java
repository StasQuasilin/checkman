package api.transport;

import api.ServletAPI;
import constants.Branches;
import entity.documents.LoadPlan;
import entity.transport.TransportationNote;
import org.json.simple.JSONObject;
import utils.U;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 02.07.2019.
 */
@WebServlet(Branches.API.SAVE_NOTE)
public class SaveNoteServletAPI extends ServletAPI {

    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            String noteText = String.valueOf(body.get("note"));
            if (U.exist(noteText)) {
                Object planId = body.get("plan");
                noteText = noteText.replaceAll("  ", " ").trim().toLowerCase();
                noteText = noteText.substring(0, 1).toUpperCase() + noteText.substring(1);
                LoadPlan plan = dao.getLoadPlanById(planId);
                TransportationNote note = new TransportationNote();
                note.setTime(new Timestamp(System.currentTimeMillis()));
                note.setCreator(getWorker(req));
                note.setTransportation(plan.getTransportation());
                note.setNote(noteText);
                dao.save(note);
                write(resp, SUCCESS_ANSWER);
            }
        }
    }
}
