package controllers.seals;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 07.04.2019.
 */
@WebServlet(Branches.UI.SEAL_LIST)
public class SealsList extends IUIServlet {

    private static final Subscriber[] subs = new Subscriber[]{Subscriber.SEALS};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, Titles.SEAL_LIST);
        req.setAttribute(EDIT, Branches.UI.SEAL_CREATE);
        req.setAttribute(FIND, Branches.UI.SEAL_FIND);
        req.setAttribute(OFF, Branches.UI.SEAL_OFF);
        req.setAttribute(SHOW, Branches.UI.SEAL_SHOW);
        req.setAttribute(SUBSCRIBE, subs);
        req.setAttribute(CONTENT, "/pages/seals/sealsList.jsp");
        show(req, resp);
    }
}
