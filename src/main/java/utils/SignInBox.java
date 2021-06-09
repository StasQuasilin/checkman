package utils;

import constants.Branches;
import constants.Constants;
import entity.Role;
import entity.User;
import entity.Worker;
import entity.answers.ErrorAnswer;
import entity.answers.Answer;
import org.apache.log4j.Logger;
import utils.access.UserBox;
import utils.answers.SuccessAnswer;
import utils.hibernate.dao.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Kvasik on 24.06.2019.
 */
public class SignInBox implements Constants{

    private static Logger log = Logger.getLogger(SignInBox.class);
    final static UserDAO dao = new UserDAO();
    final static LanguageBase lb = LanguageBase.getBase();
    private static final UserBox userBox = UserBox.getInstance();

    public static synchronized Answer signIn(HttpServletRequest req, String uid, String password, String hash) {
        Answer answer;
        User user = dao.getUserByUUID(uid);

        if (user != null ){
            final String passwordHash = user.getPasswordHash();
            if (passwordHash.equals(hash)){
                answer = new SuccessAnswer();
                answer.add(Constants.USING, Constants.HASH);
                System.out.println("Login via hash");
                login(req, user, answer);
            } else {
                final String userPassword = user.getPassword();
                if (userPassword.equals(password)) {
                    answer = new SuccessAnswer();
                    login(req, user, answer);
                    answer.add(Constants.USING, Constants.PASSWORD);
                    System.out.println("Login via password");
                } else {
                    final String msg = lb.get(Languages.WRONG_PASSWORD);
                    answer = new ErrorAnswer("msd");
                    answer.add(Constants.MSG, msg);
                    log.error("Wrong password");
                }
            }
        } else {
            final String msg = lb.get(Languages.NO_USER);
            answer = new ErrorAnswer("msd");
            answer.add(Constants.MSG, msg);
            log.error("User not found");
        }

        return answer;
    }

    private static void login(HttpServletRequest req, User user, Answer answer) {
        String token = userBox.addUser(user, IpUtil.getIp(req), req.getSession().getId());
        final Worker worker = user.getWorker();
        final Role role = worker.getRole();

        answer.add("redirect", Branches.UI.HOME);
        answer.add(USER, worker.toJson());
        answer.add(UID, user.getUid());
        answer.add(ROLE, role.toString());
        answer.add(TOKEN, token);

        //
        final HttpSession session = req.getSession();
        session.setAttribute(ID, user.getId());
        session.setAttribute(TOKEN, token);
        final String language = worker.getLanguage();
        session.setAttribute(LOCALE, language);
        session.setAttribute(LANG, language);
        session.setAttribute(WORKER, worker);
        session.setAttribute(USER, worker.getPerson().getValue());
        session.setAttribute(UID, user.getUid());
        session.setAttribute(ROLE, role);
        session.setAttribute(VIEW, role);
    }
}
