package api.sign;

import api.ServletAPI;
import constants.Branches;
import entity.User;
import entity.answers.ErrorAnswer;
import entity.answers.IAnswer;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.LanguageBase;
import utils.answers.SuccessAnswer;
import utils.email.EmailSender;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 20.06.2019.
 */
@WebServlet(Branches.API.PASSWORD_RESTORE)
public class PasswordRestoreServletAPI extends ServletAPI {

    private final LanguageBase lb = LanguageBase.getBase();
    private final HashMap<String, Long> sendEmails = new HashMap<>();
    final

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        boolean b = sendEmails.containsKey(sessionId);
        if (b) {
            long now = System.currentTimeMillis();
            Long time = sendEmails.get(sessionId);
            b = now - time < 1000 * 60;
            if (!b){
                sendEmails.remove(sessionId);
            }
        }
        IAnswer answer;
        if (!b) {
            sendEmails.put(sessionId, System.currentTimeMillis());

            JSONObject body = parseBody(req);
            if (body != null) {
                String email = (String) body.get("email");
                User user = dao.getUserByEmail(email);

                if (user != null) {
                    String language = user.getWorker().getLanguage();
                    try {
                        EmailSender.getInstance().sendEmail(
                                email,
                                lb.get(language, "password.restore.email.subject"),
                                String.format(
                                        lb.get(language, "password.restore.email.text"),
                                        user.getWorker().getValue(),
                                        new String(Base64.getDecoder().decode(user.getPassword()))));
                        answer = new SuccessAnswer();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        answer = new ErrorAnswer("msg", String.format(lb.get(language, "password.restore.cant.send"), e.getCause()));
                    }
                } else {
                    answer = new ErrorAnswer("msg", String.format(lb.get("password.restore.user.not.fount"), email));
                }

            } else {
                answer = new ErrorAnswer("msg", "Empty body");
            }
        } else {
            answer = new ErrorAnswer("msg", lb.get("password.restore.check"));
        }
        write(resp, parser.toJson(answer).toJSONString());
    }
}
