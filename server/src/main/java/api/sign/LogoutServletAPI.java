package api.sign;

import api.ServletAPI;
import constants.Branches;
import filters.SignInFilter;
import org.apache.log4j.Logger;
import utils.access.UserBox;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by szpt_user045 on 04.04.2019.
 */
@WebServlet(Branches.Sign.LOGOUT)
public class LogoutServletAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(LogoutServletAPI.class);
    private static final String success = parser.toJson( new SuccessAnswer("redirect", Branches.UI.SING_IN)).toJSONString();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserBox.getUserBox().remove(String.valueOf(req.getSession().getAttribute(SignInFilter.TOKEN)));
        log.info("Clear session");
        Enumeration<String> attributeNames = req.getSession().getAttributeNames();
        while (attributeNames.hasMoreElements()){
            req.getSession().removeAttribute(attributeNames.nextElement());
        }

        write(resp, success);
    }
}
