package api.retail;

import api.ServletAPI;
import constants.Branches;
import entity.Worker;
import entity.deal.Contract;
import entity.deal.ContractProduct;
import entity.transport.Transportation2;
import entity.transport.TransportationDocument;
import entity.transport.TransportationProduct;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.contracts.ContractSaver;
import utils.transport.TransportationSaver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by szpt_user045 on 03.12.2019.
 */
@WebServlet(Branches.API.RETAIL_EDIT)
public class EditRetailServletAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(EditRetailServletAPI.class);
    private ContractSaver contractSaver = new ContractSaver();
    private TransportationSaver transportationSaver = new TransportationSaver();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Worker worker = getWorker(req);

            Transportation2 transportation = transportationSaver.saveTransportation(body, worker);


            HashMap<Contract, TransportationDocument> documents = new HashMap<>();
            HashMap<ContractProduct, TransportationProduct> products = new HashMap<>();

            for (TransportationDocument document : transportation.getDocuments()){
                for (TransportationProduct product : document.getProducts()){
                    ContractProduct contractProduct = product.getContractProduct();
                    Contract contract = contractProduct.getContract();
                    if (!documents.containsKey(contract)){
                        documents.put(contract, document);
                    }
                    if (!products.containsKey(contractProduct)){
                        products.put(contractProduct, product);
                    }
                }
            }
            ArrayList<TransportationProduct> saveTransportationProducts = new ArrayList<>();
            for (Object d : (JSONArray) body.get(DEALS)){
                JSONObject object = (JSONObject) d;
                Contract contract = contractSaver.saveContract(object, worker);
            }

            write(resp, SUCCESS_ANSWER);
        }
    }
}
