package controllers.api.references;

import constants.Apis;
import controllers.api.API;
import entity.references.Organisation;
import org.json.simple.JSONArray;
import utils.answer.Answer;
import utils.answer.ErrorAnswer;
import utils.answer.SuccessAnswer;
import utils.db.dao.DaoService;
import utils.db.dao.references.OrganisationDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.KEY;
import static constants.Keys.RESULT;

@WebServlet(Apis.FIND_ORGANISATION)
public class FindOrganisationAPI extends API {

    private final OrganisationDAO organisationDAO = DaoService.getOrganisationDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final JsonObject body = parseBody(req);
        Answer answer;
        if(body != null){
            System.out.println(body);
            JSONArray array = new JSONArray();
            final String key = body.getString(KEY);
            for (Organisation organisation : organisationDAO.find(key)){
                array.add(organisation.toJson());
            }
            answer = new SuccessAnswer();
            answer.addAttribute(RESULT, array);
        }else {
            answer = new ErrorAnswer();
        }
        write(resp, answer);
    }
}
