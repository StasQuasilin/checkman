package controllers.rails;

import constants.Branches;
import controllers.IModal;
import entity.rails.Train;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 30.05.2019.
 */
@WebServlet(Branches.UI.RAIL_EDIT)
public class RailsEdit extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "title.rails.edit");
        req.setAttribute("modalContent", "/pages/rails/railsEdit.jsp");
        req.setAttribute("save", Branches.API.RAILS_SAVE);
        show(req, resp);
    }
}
