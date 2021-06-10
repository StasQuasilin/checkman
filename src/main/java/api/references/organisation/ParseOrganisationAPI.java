package api.references.organisation;

import api.ServletAPI;
import constants.Branches;
import entity.Worker;
import entity.notifications.Notification;
import entity.organisations.Organisation;
import entity.organisations.OrganisationType;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.LanguageBase;
import utils.OrganisationInfoUtil;
import utils.answers.SuccessAnswer;
import utils.hibernate.dbDAO;
import utils.notifications.SomeNotificator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.PARSE_ORGANISATION)
public class ParseOrganisationAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(ParseOrganisationAPI.class);
    private final SomeNotificator notificator = new SomeNotificator();
    private final LanguageBase lb = LanguageBase.getBase();
    private static final String SUCCESS_NOTIFICATION = "notification.organisation.create.success";
    private final OrganisationInfoUtil infoUtil = new OrganisationInfoUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if (body != null) {
            log.info(body);
            Organisation organisation = parseOrganisation(String.valueOf(body.get(KEY)), dao, getWorker(req));

            Worker worker = getWorker(req);
            String notificationText = String.format(lb.get(worker.getLanguage(), SUCCESS_NOTIFICATION), organisation.getValue());
            JSONObject notificationJson = new Notification(notificationText).toJson();
            notificator.sendNotification(worker, notificationJson);
            pool.put(notificationJson);

            JSONObject answerJson = new SuccessAnswer(RESULT, organisation.toJson()).toJson();
            write(resp, answerJson.toJSONString());
            pool.put(answerJson);

//            infoUtil.checkOrganisation(organisation, worker);

        } else {
            write(resp, EMPTY_BODY);
        }
    }

    public synchronized Organisation parseOrganisation(String origin, dbDAO dao, Worker worker){
        //*
        origin = origin.replaceAll("\"", SPACE).trim().toUpperCase();
        String name = SPACE + origin + SPACE;

        List<OrganisationType> typeList = dao.getOrganisationTypeList();
        String[] types = new String[typeList.size()];
        int i = 0;
        for (OrganisationType organisationType : typeList){
            types[i++] = organisationType.getName();
        }
        Pattern pattern = Pattern.compile("^\\s(" + String.join("|", types) + ")\\s|\\s(" + String.join("|", types) + ")\\s$");
        Matcher matcher = pattern.matcher(name.toUpperCase());
        String type = "";
        if (matcher.find()){
            type = matcher.group();
            name = name.replaceFirst(type, EMPTY);
        }

        type = type.trim();
        name = name.trim();
        name = name.replaceAll("^[^а-яА-Яa-zA-Z0-9]|[^а-яА-Яa-zA-Z0-9]$", "");

        if (name.isEmpty()) {
            name = origin;
            type = null;
        }
         //*/

        Organisation organisation = dao.findOrganisation(type, origin);

        if (organisation == null) {
            organisation = new Organisation();
            organisation.setType(type);
            organisation.setName(name);
            organisation.setCreate(new ActionTime(worker));
            dao.save(organisation.getCreate());
            dao.save(organisation);
        }

        return organisation;
    }
}
