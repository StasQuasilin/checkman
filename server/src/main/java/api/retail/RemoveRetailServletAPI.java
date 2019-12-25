package api.retail;

import api.ServletAPI;
import constants.Branches;
import entity.transport.Transportation2;
import entity.transport.TransportationDocument;
import entity.transport.TransportationProduct;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Kvasik on 25.12.2019.
 */
@WebServlet(Branches.API.RETAIL_REMOVE)
public class RemoveRetailServletAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Transportation2 transportation = dao.getObjectById(Transportation2.class, body.get(ID));

            for (TransportationDocument document : transportation.getDocuments()){
                dao.remove(document.getProducts().toArray());
            }
            dao.remove(transportation.getDocuments().toArray());
            dao.remove(transportation);

            write(resp, SUCCESS_ANSWER);
        }
    }
}
