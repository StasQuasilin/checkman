package api.references.organisation;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.answers.Answer;
import entity.organisations.Organisation;
import entity.organisations.OrganisationType;
import entity.organisations.OrganisationWe;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.CodeUtil;
import utils.U;
import utils.UpdateUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.EDIT_ORGANISATION)
public class EditOrganisationAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            System.out.println(body);

            Worker worker = getWorker(req);
            Organisation organisation = saveOrganisation((JSONObject) body.get(ORGANISATION), worker);
            final String s = String.valueOf(body.get(TYPE));
            if (U.exist(s)) {
                try {
                    saveOrganisationType(parser.parse(s), organisation, worker);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            Answer answer = new SuccessAnswer(RESULT, organisation.toJson());
            JSONObject json = answer.toJson();
            write(resp, json.toJSONString());
            pool.put(json);
        } else {
            write(resp, EMPTY_BODY);
        }
    }

    private synchronized void saveOrganisationType(JSONObject json, Organisation organisation, Worker worker) {
        OrganisationType organisationType = dao.getOrganisationTypeByName(organisation.getType());
        if (organisationType == null){
            organisationType = new OrganisationType();
            organisationType.setName(organisation.getType());
            dao.save(organisationType);
        }
        if (json.get(FULL_NAME) != null) {
            String fullName = String.valueOf(json.get(FULL_NAME)).trim().toUpperCase();
            if (U.exist(fullName)) {
                if (!U.exist(organisationType.getFullName()) || !organisationType.getFullName().equals(fullName)) {
                    organisationType.setFullName(fullName);
                    dao.save(organisationType);
                }
            }
        }
    }

    private synchronized Organisation saveOrganisation(JSONObject json, Worker worker){
        Organisation organisation = dao.getObjectById(Organisation.class, json.get(ID));;
        boolean save = false;
        if (organisation == null) {
            ActionTime actionTime = new ActionTime(worker);
            dao.save(actionTime);
            organisation = new Organisation();
            organisation.setCreate(actionTime);
        }

        String code = String.valueOf(json.get(CODE));
        if (U.exist(code)){
            if (CodeUtil.validCode(code)) {
                if (!U.exist(organisation.getCode()) || !organisation.getCode().equals(code)) {
                    organisation.setCode(code);
                    save = true;
                }
            }
        }
        String name = String.valueOf(json.get(NAME));
        name = name.trim().toUpperCase();

        if (!U.exist(organisation.getName()) || !organisation.getName().equals(name)) {
            organisation.setName(name);
            save = true;
        }


        String type = String.valueOf(json.get(TYPE));
        type = type.trim().toUpperCase();
        if (!U.exist(organisation.getType()) || !organisation.getType().equals(type)){
            organisation.setType(type);
            save = true;
        }
        if (save){
            dao.save(organisation);
        }
        OrganisationWe organisationWe = organisation.getOrganisationWe();
        if (json.containsKey(Constants.WE)){
            if(Boolean.parseBoolean(String.valueOf(json.get(Constants.WE)))){
                if (organisationWe == null){
                    organisationWe = new OrganisationWe();
                    organisationWe.setOrganisation(organisation);
                    dao.save(organisationWe);
                }
            }  else if (organisationWe != null){
                dao.remove(organisationWe);
            }
        }
        return organisation;
    }
}
