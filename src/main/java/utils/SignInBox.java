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

    private static Logger log = Logger.getLogger(SignInBox.class);
    final static dbDAO dao = dbDAOService.getDAO();
    final static LanguageBase lb = LanguageBase.getBase();

    public static synchronized IAnswer signIn(HttpServletRequest req, String uid, String password) throws IOException {
        IAnswer answer;
        log.info("Sign in by user token " + uid);
        User user = dao.getUserByUID(uid);

        if (user != null ){
            if (user.getPassword().equals(password)) {
                answer = new SuccessAnswer();
                answer.add("redirect", Branches.UI.HOME);
                log.info("Success, user " + user.getWorker().getPerson().getValue());
                req.getSession().setAttribute("token", UserBox.getUserBox().addUser(new UserData(user, req.getSession())));
                req.getSession().setAttribute("lang", user.getWorker().getLanguage());
                req.getSession().setAttribute("worker", user.getWorker());
                req.getSession().setAttribute("uid", user.getUid());
                req.getSession().setAttribute("role", user.getRole());
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
