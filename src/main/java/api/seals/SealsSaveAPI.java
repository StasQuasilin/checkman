package api.seals;

import api.IAPI;
import constants.Branches;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 07.04.2019.
 */
@WebServlet(Branches.API.SEAL_SAVE)
public class SealsSaveAPI extends IAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
