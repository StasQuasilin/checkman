package controllers.api.references;

import constants.Apis;
import controllers.api.API;
import entity.Worker;
import org.json.simple.JSONArray;
import utils.answer.Answer;
import utils.answer.ErrorAnswer;
import utils.answer.SuccessAnswer;
import utils.db.dao.DaoService;
import utils.db.dao.workers.WorkerDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.KEY;
import static constants.Keys.RESULT;

@WebServlet(Apis.FIND_WORKER)
public class FindWorkerApi extends API {

    private WorkerDAO workerDAO = DaoService.getWorkerDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final JsonObject body = parseBody(req);
        Answer answer;
        if(body != null){
            final String key = body.getString(KEY);
            if (key != null) {
                JSONArray array = new JSONArray();
                for (Worker worker : workerDAO.findWorker(key)) {
                    array.add(worker.toJson());
                }
                answer = new SuccessAnswer();
                answer.addAttribute(RESULT, array);
            } else {
                answer = new ErrorAnswer();
            }
        } else {
            answer = new ErrorAnswer();
        }
        write(resp, answer);
    }
}
