package api.laboratory.protocols;

import api.ServletAPI;
import constants.Branches;
import entity.laboratory.Protocol;
import entity.products.Product;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by szpt_user045 on 18.12.2019.
 */
@WebServlet(Branches.API.PROTOCOL_EDIT)
public class ProtocolEditAPI extends ServletAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Protocol protocol = dao.getObjectById(Protocol.class, body.get(ID));
            if (protocol == null){
                protocol = new Protocol();
            }
            Date date = Date.valueOf(String.valueOf(body.get(DATE)));
            protocol.setDate(date);

            Product product = dao.getObjectById(Product.class, body.get(PRODUCT));
            protocol.setProduct(product);

            String number = String.valueOf(body.get(NUMBER));
            protocol.setNumber(number);

            ActionTime createTime = protocol.getCreateTime();
            if (createTime == null){
                createTime = new ActionTime(getWorker(req));
                dao.save(createTime);
                protocol.setCreateTime(createTime);
            }
            dao.save(protocol);
            write(resp, SUCCESS_ANSWER);
        }
    }
}
