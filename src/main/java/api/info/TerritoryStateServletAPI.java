package api.info;

import api.ServletAPI;
import constants.Branches;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.TERRITORY_INFO)
public class TerritoryStateServletAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
