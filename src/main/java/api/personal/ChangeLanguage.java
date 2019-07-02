package api.personal;

import api.API;
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
public class ChangeLanguage extends API {
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
                write(resp, answer);
            }
        } else {
            write(resp, emptyBody);
        }
    }
}
