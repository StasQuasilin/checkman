package api.seals;

import api.ServletAPI;
import constants.Branches;
import entity.Worker;
import entity.answers.Answer;
import entity.answers.ErrorAnswer;
import entity.seals.Seal;
import entity.seals.SealBatch;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.U;
import utils.UpdateUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

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
        Answer answer;
        if(body != null) {
            String prefix = String.valueOf(body.get(PREFIX));
            int number = Integer.parseInt(String.valueOf(body.get(NUMBER)));
            String suffix = String.valueOf(body.get(SUFFIX));
            int quantity = Integer.parseInt(String.valueOf(body.get(QUANTITY)));

            final Worker worker = getWorker(req);

            String name = doSealName(prefix, number, suffix) + DELIMITER + doSealName(prefix, (number + quantity - 1), suffix);
            HashMap<String, Object> param = new HashMap<>();
            param.put(TITLE, name);
            final List<SealBatch> batchList = dao.getObjectsByParams(SealBatch.class, param);
            if (batchList.size() > 0){
                answer = new ErrorAnswer("Batch already exist");
            } else {
                log.info(worker.getValue() + " put seals " + name);
                if (quantity > 0) {
                    SealBatch batch = new SealBatch();
                    ActionTime time = new ActionTime(worker);
                    batch.setCreated(time);
                    batch.setTitle(name);
                    batch.setFree(quantity);
                    batch.setTotal(quantity);

                    dao.save(time, batch);
                    for (int i = 0; i < quantity; i++) {
                        final String sealName = doSealName(prefix, number, suffix);
                        Seal seal = dao.getSealByName(sealName);
                        if (seal == null){
                            seal  = new Seal();
                            seal.setBatch(batch);
                            seal.setNumber(number);
                            seal.setValue(sealName);
                            dao.save(seal);
                        }
                        number++;
                    }
                    answer = new SuccessAnswer();
                    updateUtil.onSave(batch);
                } else {
                    answer = new ErrorAnswer("Seal quantity must be great that zero!");
                }
            }
        } else {
            answer = new ErrorAnswer("Empty body?");
        }
        write(resp, answer);
    }
    synchronized String doSealName(String prefix, long number, String suffix){
        return (U.exist(prefix) ? prefix.toUpperCase() + HYPHEN : EMPTY) + number +
                (U.exist(suffix) ? HYPHEN + suffix.toUpperCase() : EMPTY);
    }
}
