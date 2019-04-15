package controllers.references;

import constants.Branches;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 15.04.2019.
 */
@WebServlet(Branches.UI.REFERENCES)
public class ReferencesList extends IUIServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReferenceTabs tabs = ReferenceTabs.valueOf(req.getParameter("tab"));
        switch (tabs){

        }
        show(req, resp);
    }
    enum ReferenceTabs{
        drivers,
        vehicles,
        organisations
    }
}
