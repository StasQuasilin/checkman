package controllers;

import constants.Constants;
import entity.Worker;
import utils.LanguageBase;
import utils.PostUtil;
import utils.hibernate.Hibernator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public class IServlet extends HttpServlet {
    public static final Hibernator hibernator = Hibernator.getInstance();
    public static final LanguageBase lb = LanguageBase.getBase();

    public Worker getWorker(HttpServletRequest req){
        return (Worker)req.getSession().getAttribute(Constants.WORKER);
    }

}
