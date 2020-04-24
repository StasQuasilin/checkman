package api.references;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import org.json.simple.JSONObject;
import utils.CodeUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.API.CODE_VALID)
public class CodeValidAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            String code = String.valueOf(body.get(CODE));
            JSONObject json = new SuccessAnswer(Constants.RESULT, CodeUtil.validCode(code)).toJson();
            write(resp, json);
        }
    }
}
