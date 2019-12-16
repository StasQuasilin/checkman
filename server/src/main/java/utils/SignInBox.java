package utils;

import constants.Branches;
import constants.Constants;
import entity.User;
import entity.answers.ErrorAnswer;
import entity.answers.IAnswer;
import org.apache.log4j.Logger;
import utils.access.UserBox;
import utils.access.UserData;
import utils.answers.SuccessAnswer;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Kvasik on 24.06.2019.
 */
public class SignInBox {

    private static final String TOKEN = "token";
    private static Logger log = Logger.getLogger(SignInBox.class);
    final static dbDAO dao = dbDAOService.getDAO();
    final static LanguageBase lb = LanguageBase.getBase();
    private static JsonParser parser = new JsonParser();
    private static final UserBox userBox = UserBox.getUserBox();

    public static synchronized IAnswer signIn(HttpServletRequest req, String uid, String password) throws IOException {
        IAnswer answer;
        User user = dao.getUserByUID(uid);
        if (user == null) {
            user = dao.getUserByEmail(uid);
        }

        if (user != null ){
            if (user.getPassword().equals(password)) {
                String token = userBox.addUser(user, IpUtil.getIp(req), req.getSession().getId());
                answer = new SuccessAnswer();
                answer.add("redirect", Branches.UI.HOME);
                answer.add("user", parser.toJson(user.getWorker()));
                answer.add("uid", user.getUid());
                answer.add("role", user.getWorker().getRole().toString());
                answer.add(TOKEN, token);
                //

                req.getSession().setAttribute(TOKEN, token);
                req.getSession().setAttribute("lang", user.getWorker().getLanguage());
                req.getSession().setAttribute("worker", user.getWorker());
                req.getSession().setAttribute("user", user.getWorker().getPerson().getValue());
                req.getSession().setAttribute("uid", user.getUid());
                req.getSession().setAttribute("role", user.getWorker().getRole());
            } else {
                answer = new ErrorAnswer("msd", lb.get(Constants.Languages.WRONG_PASSWORD));
                log.error("Wrong password");
            }
        } else {
            answer = new ErrorAnswer("msd", lb.get(Constants.Languages.NO_USER));
            log.error("User not found");
        }

        return answer;
    }
}
