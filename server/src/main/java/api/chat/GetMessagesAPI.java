package api.chat;

import api.ServletAPI;
import constants.Branches;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 06.08.2019.
 */
@WebServlet(Branches.API.GET_CHAT)
public class GetMessagesAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long chat = (long) body.get("chat");

            JSONArray array = pool.getArray();
            array.addAll(dao.getLimitMessagesByChat(chat).stream().map(parser::toJson).collect(Collectors.toList()));
            JSONObject object = pool.getObject();
            object.put("messages", array);
            write(resp, object.toJSONString());
            pool.put(object);
        }
    }
}
