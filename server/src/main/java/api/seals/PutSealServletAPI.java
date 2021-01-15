package api.seals;

import api.ServletAPI;
import constants.Branches;
import entity.answers.Answer;
import entity.seals.Seal;
import entity.seals.SealBatch;
import entity.transport.Transportation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.SealsUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by szpt_user045 on 08.04.2019.
 */
@WebServlet(Branches.API.SEAL_PUT)
public class PutSealServletAPI extends ServletAPI {

    private SealsUtil sealsUtil = new SealsUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {

            Seal seal = dao.getObjectById(Seal.class, body.get(SEAL));
            Transportation transportation = dao.getObjectById(Transportation.class, body.get(TRANSPORTATION));

            SealBatch batch = null;
            if (seal != null) {
                seal.setCargo(transportation);
                dao.save(seal);
                batch = seal.getBatch();
            }
            ArrayList<Seal> offer = new ArrayList<>();
            if (body.get(DO_OFFER) != null) {
                List<Seal> seals = dao.getSealsByTransportation(transportation);
                Collections.sort(seals);


                for (int i = 0; i < seals.size() - 1; i++) {
                    Seal s1 = seals.get(i);
                    Seal s2 = seals.get(i + 1);


                    int dif = s2.getNumber() - s1.getNumber() - 1;

                    if (dif > 0 && dif < 14) {
                        System.out.println("Current seal: " + s1);
                        System.out.println("Next seal:" + s2);
                        System.out.println("Different between: " + dif);
                        for (int j = 0; j < dif; j++) {
                            int target = s1.getNumber() + j + 1;
                            System.out.print("\t-->Target: " + target);
                            Seal byNumber = dao.getSealByNumber(target);
                            if (byNumber != null) {
                                System.out.println(", done.");
                                offer.add(byNumber);
                            } else {
                                System.out.println(", none.");
                            }
                        }
                    }
                }
            }
            System.out.println("Total offers: " + offer.size());
            Answer answer = new SuccessAnswer();
            if (offer.size() > 0){
                JSONArray array = pool.getArray();
                for(Seal s : offer){
                    array.add(s.toJson());
                }
                answer.add(OFFER, array);
            }
            offer.clear();
            JSONObject json = answer.toJson();
            write(resp, json.toJSONString());
            pool.put(json);

            if (batch != null){
                sealsUtil.checkBatch(batch);
            }
        } else {
            write(resp, EMPTY_BODY);
        }

    }
}
