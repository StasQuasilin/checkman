package utils;

import constants.Branches;
import constants.Constants;
import entity.User;
import entity.answers.ErrorAnswer;
import entity.answers.IAnswer;
import entity.notifications.Notification;
import org.apache.log4j.Logger;
import utils.access.UserBox;
import utils.access.UserData;
import utils.answers.SuccessAnswer;
import utils.hibernate.dao.HibernateDAO;
import utils.hibernate.dao.UserDAO;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;
import utils.notifications.Notificator;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Kvasik on 24.06.2019.
 */
public class SignInBox implements Constants{

    private static Logger log = Logger.getLogger(SignInBox.class);
    final static UserDAO dao = new UserDAO();
    final static LanguageBase lb = LanguageBase.getBase();
    final static Crypto crypto = new Crypto();
    private static final UserBox userBox = UserBox.getUserBox();

    public static synchronized IAnswer signIn(HttpServletRequest req, String uid, String passwordHash) {
        IAnswer answer;
        User user = dao.getUserByUUID(uid);

        if (user != null ){
            final String userHash = user.getPasswordHash();
            log.info(userHash + ".equals(" + passwordHash + ")=" + userHash.equals(passwordHash));
            if (userHash.equals(passwordHash)) {
                String token = userBox.addUser(user, IpUtil.getIp(req), req.getSession().getId());
                answer = new SuccessAnswer();
                answer.add("redirect", Branches.UI.HOME);
                answer.add(USER, user.getWorker().toJson());
                answer.add(UID, user.getUid());
                answer.add(ROLE, user.getWorker().getRole().toString());
                answer.add(TOKEN, token);
                //
                req.getSession().setAttribute(ID, user.getId());
                req.getSession().setAttribute(TOKEN, token);
                req.getSession().setAttribute(LOCALE, user.getWorker().getLanguage());
                req.getSession().setAttribute("lang", user.getWorker().getLanguage());
                req.getSession().setAttribute(WORKER, user.getWorker());
                req.getSession().setAttribute(USER, user.getWorker().getPerson().getValue());
                req.getSession().setAttribute(UID, user.getUid());
                req.getSession().setAttribute(ROLE, user.getWorker().getRole());


            } else {
                answer = new ErrorAnswer("msd", lb.get(Languages.WRONG_PASSWORD));
                log.error("Wrong password");
            }
        } else {
            answer = new ErrorAnswer("msd", lb.get(Languages.NO_USER));
            log.error("User not found");
        }

        return answer;
    }
}
