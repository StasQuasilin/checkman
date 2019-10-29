package api.personal;

import api.ServletAPI;
import constants.Branches;
import entity.Worker;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 02.07.2019.
 */
@WebServlet(Branches.API.CHANGE_LANGUAGE)
public class ChangeLanguage extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            String lang = (String) body.get("lang");
            Worker worker = getWorker(req);
            if (!worker.getLanguage().equals(lang)){
                worker.setLanguage(lang);
                dao.save(worker);
                req.getSession().setAttribute("lang", lang);
                write(resp, SUCCESS_ANSWER);
            }
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
