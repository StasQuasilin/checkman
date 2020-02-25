package controllers.seals;

import constants.Branches;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 20.02.2020.
 */
@WebServlet(Branches.UI.SEAL_FIND)
public class SealFind extends IModal {
    private static final String _TITLE = "title.seal.find";
    private static final String _CONTENT = "/pages/seals/sealFind.jsp";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(FIND, Branches.API.FIND_SEALS);
        show(req, resp);
    }
}
