package api.seals;

import api.ServletAPI;
import constants.Branches;
import entity.seals.Seal;
import entity.seals.SealBatch;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.U;
import utils.UpdateUtil;

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
public class SaveSealsAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(SaveSealsAPI.class);
    public static final String DELIMITER = " ... ";
    public static final String HYPHEN = "-";
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            String prefix = String.valueOf(body.get(PREFIX));
            int number = Integer.parseInt(String.valueOf(body.get(NUMBER)));
            String suffix = String.valueOf(body.get(SUFFIX));
            int quantity = Integer.parseInt(String.valueOf(body.get(QUANTITY)));

            log.info(getWorker(req).getValue() + " put seals " + doSealName(prefix, number, suffix) + " ... " + doSealName(prefix, number + quantity, suffix));
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
                    seal.setNumber(number);
                    seal.setValue(doSealName(prefix, number, suffix));
                    if (i == 0) {
                        builder.append(seal.getValue()).append(DELIMITER);
                    } else if (i == quantity -1) {
                        builder.append(seal.getValue());
                    }
                    dao.save(seal);
                    number++;
                }

                batch.setTitle(builder.toString());
                dao.save(batch);

                body.clear();
                updateUtil.onSave(batch);
            }
            write(resp, SUCCESS_ANSWER);

        } else {
            write(resp, EMPTY_BODY);
        }

    }
    synchronized String doSealName(String prefix, long number, String suffix){
        return (U.exist(prefix) ? prefix.toUpperCase() + HYPHEN : EMPTY) + number +
                (U.exist(suffix) ? HYPHEN + suffix.toUpperCase() : EMPTY);
    }
}
