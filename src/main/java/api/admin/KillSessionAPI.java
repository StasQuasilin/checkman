package api.admin;

import api.ServletAPI;
import api.sockets.SubscribesAPI;
import constants.Branches;
import constants.Constants;
import entity.answers.Answer;
import entity.answers.ErrorAnswer;
import org.json.simple.JSONObject;
import utils.UpdateUtil;
import utils.access.UserBox;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.API.KILL_SESSION)
public class KillSessionAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();
    private final UserBox userBox = UserBox.getInstance();
    private final SubscribesAPI subscribesAPI = SubscribesAPI.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        Answer answer;
        if (body != null){
            String session = String.valueOf(body.get(Constants.SESSION));
            final boolean kill = userBox.remove(session);
            subscribesAPI.killSession(session);
            if(kill){
                answer = new SuccessAnswer();
            } else {
                answer = new ErrorAnswer("Session not found");
            }
        }else {
            answer = new ErrorAnswer("Empty answer body");
        }
        write(resp, answer);
    }
}
