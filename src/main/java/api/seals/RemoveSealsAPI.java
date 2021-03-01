package api.seals;

import api.ServletAPI;
import constants.Branches;
import entity.seals.Seal;
import entity.seals.SealBatch;
import org.json.simple.JSONObject;
import utils.SealsUtil;
import utils.UpdateUtil;
import utils.hibernate.DeprecatedDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(Branches.API.SEALS_REMOVE)
public class RemoveSealsAPI extends ServletAPI {

    private final DeprecatedDAO dao = new DeprecatedDAO();
    private final SealsUtil sealsUtil = new SealsUtil();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if(body != null){
            final Object id = body.get(ID);
            final SealBatch batch = dao.getObjectById(SealBatch.class, id);
            sealsUtil.removeBatch(batch);

            write(resp, SUCCESS_ANSWER);
        }
    }
}
