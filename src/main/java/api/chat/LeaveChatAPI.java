package api.chat;

import api.ServletAPI;
import constants.Branches;
import org.json.simple.JSONObject;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 03.09.2019.
 */
@WebServlet(Branches.API.LEAVE_CHAT)
public class LeaveChatAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {

        }
    }
}
