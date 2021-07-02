package api.plan;

import api.ServletAPI;
import constants.Branches;
import entity.Worker;
import entity.seals.Seal;
import entity.transport.Driver;
import entity.transport.TransportUtil;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;
import entity.weight.Weight;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.SealsUtil;
import utils.UpdateUtil;
import utils.hibernate.dao.SealDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.REMOVE_PLAN)
public class CloseTransportationAPI extends ServletAPI{

    final UpdateUtil updateUtil = new UpdateUtil();
    final Logger log = Logger.getLogger(CloseTransportationAPI.class);
    private final SealDAO sealDAO = new SealDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {

            Transportation transportation = dao.getObjectById(Transportation.class, body.get(ID));
            if (transportation != null) {
                final Worker worker = getWorker(req);
                final List<Seal> seals = sealDAO.getSealsByTransportation(transportation);
                if (!transportation.any() && seals.size() == 0) {
                    dao.remove(transportation);
                    updateUtil.onRemove(transportation);
//                    writeMessage(worker, REMOVE, transportation);
                } else {
                    final LocalDate date = transportation.getDate().toLocalDate();
                    final LocalDate now = LocalDate.now();
                    boolean can = true;
                    for (TransportationProduct product : transportation.getProducts()){
                        final Weight weight = product.getWeight();
                        if (weight == null || weight.getNetto() == 0 || now.isAfter(date)){
                            can = false;
                            break;
                        }
                    }
                    if(can) {
                        transportation.setArchive(true);
                        dao.save(transportation);
                        updateUtil.onRemove(transportation);
//                        writeMessage(worker, ARCHIVE, transportation);
                    }
                }
            }
            write(resp, SUCCESS_ANSWER);
        }
    }
}
