package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.answers.Answer;
import entity.transport.Trailer;
import entity.transport.TransportUtil;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.UpdateUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.API.TRAILER_EDIT)
public class TrailerEditAPI extends ServletAPI {

    private UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        Answer answer = new SuccessAnswer();
        if (body != null){
            Trailer trailer = dao.getObjectById(Trailer.class, body.get(ID));
            if (trailer == null){
                trailer = new Trailer();
            }
            final String number = String.valueOf(body.get(NUMBER));
            if (trailer.getNumber() == null || !trailer.getNumber().equals(number)){
                trailer.setNumber(number);
                dao.save(trailer);
                answer.add(Constants.RESULT, trailer.toJson());

                for (Transportation transportation : dao.getTransportationByTrailer(trailer)){
                    TransportUtil.setTrailer(transportation, trailer);
                    updateUtil.onSave(transportation);
                }
            }
        }
        write(resp, answer);

    }
}
