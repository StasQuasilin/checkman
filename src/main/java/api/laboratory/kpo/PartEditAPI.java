package api.laboratory.kpo;

import api.API;
import bot.BotFactory;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.kpo.KPOPart;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by szpt_user045 on 22.05.2019.
 */
@WebServlet(Branches.API.KPO_PART_EDIT)
public class PartEditAPI extends API {

    private final Logger log = Logger.getLogger(PartEditAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body);
            body.get("date");
            KPOPart part;
            long id = -1;
            if (body.containsKey(Constants.ID)){
                id = (long) body.get(Constants.ID);
            }
            if (id != -1){
                part = hibernator.get(KPOPart.class, "id", id);
            } else {
                part = new KPOPart();
            }

            boolean save = false;
            LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.now());
            Timestamp timestamp = Timestamp.valueOf(dateTime);

            if (part.getDate() == null || !part.getDate().equals(timestamp)){
                part.setDate(timestamp);
                save = true;
            }

            int number = Integer.parseInt(String.valueOf(body.get("number")));
            if (part.getNumber() != number) {
                part.setNumber(number);
                save = true;
            }

            boolean organoleptic = Boolean.parseBoolean(String.valueOf(body.get("organoleptic")));
            if (part.isOrganoleptic() != organoleptic) {
                part.setOrganoleptic(organoleptic);
                save = true;
            }

            int color = Integer.parseInt(String.valueOf(body.get("color")));
            if (part.getColor() != color) {
                part.setColor(color);
                save = true;
            }

            float acid = Float.parseFloat(String.valueOf(body.get("acid")));
            if (part.getAcid() != acid) {
                part.setAcid(acid);
                save = true;
            }

            float peroxide = Float.parseFloat(String.valueOf(body.get("peroxide")));
            if (part.getPeroxide() != peroxide) {
                part.setPeroxide(peroxide);
                save = true;
            }

            boolean soap = Boolean.parseBoolean(String.valueOf(body.get("soap")));
            if (part.isSoap() != soap) {
                part.setSoap(soap);
                save = true;
            }

            if (save){
                ActionTime createTime = part.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    part.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                if (body.containsKey(Constants.CREATOR)) {
                    long creatorId = (long) body.get(Constants.CREATOR);
                    createTime.setCreator(hibernator.get(Worker.class, "id", creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                part.setCreator(worker);
                hibernator.save(part.getCreateTime(), part);
                BotFactory.getNotificator().kpoShow(part);
            }
            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }
    }
}
