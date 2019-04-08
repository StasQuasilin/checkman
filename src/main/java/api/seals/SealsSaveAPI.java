package api.seals;

import api.IAPI;
import constants.Branches;
import entity.seals.Seal;
import entity.seals.SealBatch;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.PostUtil;
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
public class SealsSaveAPI extends IAPI {

    private final Logger log = Logger.getLogger(SealsSaveAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject body = PostUtil.parseBodyJson(req);
        String prefix = String.valueOf(body.get("prefix"));
        long number = (long) body.get("number");
        String suffix = String.valueOf(body.get("suffix"));
        long quantity = (long) body.get("quantity");

        log.info(getWorker(req).getValue() + " put seals " + doSeal(prefix, number, suffix) + " ... " + doSeal(prefix, number + quantity, suffix));

        SealBatch batch = new SealBatch();
        ActionTime time = new ActionTime();
        time.setTime(new Timestamp(System.currentTimeMillis()));
        time.setCreator(getWorker(req));
        batch.setCreated(time);

        Seal[] seals = new Seal[(int) quantity];
        for(int i = 0; i < quantity; i++){
            Seal seal = new Seal();
            seal.setBatch(batch);
            seal.setNumber(doSeal(prefix, number + i, suffix));
            seals[i] = seal;
        }

        hibernator.save(time, batch);
        hibernator.save(seals);
        body.clear();
        write(resp, answer);

    }
    synchronized String doSeal(String prefix, long number, String suffix){
        return (U.exist(prefix) ? prefix.toUpperCase() + "-" : "") + number +
                (U.exist(suffix) ? "-" + suffix.toUpperCase() : "");
    }
}
