package api.retail;

import api.ServletAPI;
import constants.Branches;
import entity.transport.Transportation2;
import entity.transport.TransportationDocument2;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kvasik on 25.12.2019.
 */
@WebServlet(Branches.API.RETAIL_REMOVE)
public class RemoveRetailServletAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(RemoveRetailServletAPI.class);
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            boolean done = false;
            while (!done){
                Transportation2 transportation = dao.getObjectById(Transportation2.class, body.get(ID));
                if (transportation != null) {
                    boolean remove = true;
                    for (TransportationDocument2 document : transportation.getDocuments()) {
                        if (remove && document.getProducts().size() > 0) {
                            remove = false;
                            dao.remove(document.getProducts().toArray());
                            log.info("Remove transportation products from document " + document.getId() + "...");
                        }
                    }
                    if (remove && transportation.getDocuments().size() > 0) {
                        remove = false;
                        dao.remove(transportation.getDocuments().toArray());
                    }
                    if (remove) {
                        dao.remove(transportation);
                        done = true;
                        updateUtil.onRemove(transportation);
                    }
                } else {
                    break;
                }

            }

            log.info("\tSuccess...");

            write(resp, SUCCESS_ANSWER);
        }
    }
}
