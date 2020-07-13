package api.admin;

import api.ServletAPI;
import constants.Branches;
import entity.User;
import entity.Worker;
import org.json.simple.JSONObject;
import utils.access.UserBox;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.API.USER_DELETE)
public class DeleteUserAPI extends ServletAPI {

    private final dbDAO dao = dbDAOService.getDAO();
    UserBox userBox = UserBox.getUserBox();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            final Worker worker = dao.getObjectById(Worker.class, body.get(ID));
            final User user = dao.getUserByWorker(worker);
            dao.remove(user);
            userBox.remove(worker);
            write(resp, SUCCESS_ANSWER);
        }
    }
}
