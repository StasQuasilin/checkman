package api.seals;

import api.ServletAPI;
import constants.Branches;
import entity.seals.Seal;
import entity.seals.SealBatch;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.U;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by quasilin on 07.04.2019.
 */
@WebServlet(Branches.API.SEAL_SAVE)
public class SealsSaveServletAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(SealsSaveServletAPI.class);
    public static final String DELIMITER = " ... ";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            String prefix = String.valueOf(body.get("prefix"));
            long number = (long) body.get("number");
            String suffix = String.valueOf(body.get("suffix"));
            long quantity = (long) body.get("quantity");

            log.info(getWorker(req).getValue() + " put seals " + doSeal(prefix, number, suffix) + " ... " + doSeal(prefix, number + quantity, suffix));
            if (quantity > 0) {
                SealBatch batch = new SealBatch();
                ActionTime time = new ActionTime();
                time.setTime(new Timestamp(System.currentTimeMillis()));
                time.setCreator(getWorker(req));
                batch.setCreated(time);
                batch.setFree(quantity);
                batch.setTotal(quantity);

                dao.save(time, batch);
                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < quantity; i++) {

                    Seal seal = new Seal();
                    seal.setBatch(batch);
                    seal.setNumber(doSeal(prefix, number + i, suffix));
                    if (i == 0) {
                        builder.append(seal.getNumber()).append(DELIMITER);
                    } else if (i == quantity -1) {
                        builder.append(seal.getNumber());
                    }
                    dao.save(seal);
                }

                batch.setTitle(builder.toString());
                dao.save(batch);

                body.clear();
            }
            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, emptyBody);
        }

    }
    synchronized String doSeal(String prefix, long number, String suffix){
        return (U.exist(prefix) ? prefix.toUpperCase() + "-" : "") + number +
                (U.exist(suffix) ? "-" + suffix.toUpperCase() : "");
    }
}
