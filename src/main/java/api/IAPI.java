package api;

import constants.Constants;
import controllers.IServlet;
import entity.Worker;
import utils.JsonParser;
import utils.LanguageBase;
import utils.PostUtil;
import utils.answers.SuccessAnswer;
import utils.hibernate.Hibernator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public abstract class IAPI extends IServlet{
    public static final String answer = JsonParser.toJson(new SuccessAnswer()).toJSONString();
}
