package api.laboratory.vro;

import api.IAPI;
import constants.Branches;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 20.05.2019.
 */
@WebServlet(Branches.API.OIL_MASS_FRACTION)
public class OilMassFractionEditAPI extends IAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
