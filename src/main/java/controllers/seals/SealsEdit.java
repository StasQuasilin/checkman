package controllers.seals;

import constants.Branches;
import constants.Titles;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 07.04.2019.
 */
@WebServlet(Branches.UI.SEAL_CREATE)
public class SealsEdit extends IModal {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, Titles.SEAL_EDIT);
        req.setAttribute(SAVE, Branches.API.SEAL_SAVE);
        req.setAttribute(MODAL_CONTENT, "/pages/seals/sealsEdit.jsp");
        show(req, resp);
    }
}
