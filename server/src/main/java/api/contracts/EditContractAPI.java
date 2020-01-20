package api.contracts;

import api.ServletAPI;
import constants.Branches;
import entity.Worker;
import entity.deal.Contract;
import org.json.simple.JSONObject;
import utils.ContractUtil;
import utils.UpdateUtil;
import utils.answers.SuccessAnswer;
import utils.contracts.ContractSaver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 16.01.2020.
 */
@WebServlet(Branches.API.EDIT_CONTRACT)
public class EditContractAPI extends ServletAPI {

    private ContractSaver contractSaver = new ContractSaver();
    private UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Worker worker = getWorker(req);
            JSONObject answer = pool.getObject();
            Contract contract = contractSaver.saveContract(body, worker, answer);
            updateUtil.onSave(contract);
            JSONObject json = new SuccessAnswer().toJson();
            json.putAll(answer);
            write(resp, json.toJSONString());
            pool.put(json);
        }
    }
}
