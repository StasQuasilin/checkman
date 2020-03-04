package api.retail;

import api.ServletAPI;
import constants.Branches;
import entity.Worker;
import entity.deal.Contract;
import entity.deal.ContractProduct;
import entity.transport.Transportation2;
import entity.transport.TransportationDocument2;
import entity.transport.TransportationProduct2;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.answers.SuccessAnswer;
import utils.contracts.ContractSaver;
import utils.transport.TransportationSaver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
            SuccessAnswer successAnswer = new SuccessAnswer(TRANSPORTATION, transportation.getId());

            HashMap<Integer, TransportationDocument2> documents = new HashMap<>();
            HashMap<Integer, TransportationProduct2> products = new HashMap<>();

            for (TransportationDocument2 document : transportation.getDocuments()){
                if (document.getProducts().size() > 0) {
                    for (TransportationProduct2 product : document.getProducts()) {
                        ContractProduct contractProduct = product.getContractProduct();
                        Contract contract = contractProduct.getContract();
                        if (!documents.containsKey(contract.getId())) {
                            documents.put(contract.getId(), document);
                        }
                        if (!products.containsKey(contractProduct.getId())) {
                            products.put(contractProduct.getId(), product);
                        }
                    }
                } else {
                    dao.remove(document);
                }
            }

            ArrayList<TransportationDocument2> saveDocuments = new ArrayList<>();
            ArrayList<TransportationProduct2> saveProducts = new ArrayList<>();
            JSONObject array = pool.getObject();

            for (Object d : (JSONArray) body.get(DEALS)){
                JSONObject object = (JSONObject) d;
                Contract contract = contractSaver.saveContract(object, worker);
                String key = String.valueOf(object.get(KEY));
                array.put(key, contract.getId());

                int idx = Integer.parseInt(String.valueOf(object.get(INDEX)));
                TransportationDocument2 document;
                if (documents.containsKey(contract.getId())){
                    document = documents.remove(contract.getId());
                } else {
                    document = new TransportationDocument2();
                }
                document.setIndex(idx);

                if (document.getTransportation() == null){
                    document.setTransportation(transportation);
                }
                if (contract.getAddress() != null) {
                    document.setAddress(contract.getAddress());
                }

                saveDocuments.add(document);

                for (ContractProduct contractProduct : contract.getProducts()){
                    TransportationProduct2 product;
                    if (products.containsKey(contractProduct.getId())){
                        product = products.remove(contractProduct.getId());
                    } else {
                        product = new TransportationProduct2();
                        product.setDocument(document);
                        product.setContractProduct(contractProduct);
                    }
                    product.setAmount(contractProduct.getAmount());
                    saveProducts.add(product);
                }
            }

            saveDocuments.forEach(dao::save);
            saveProducts.forEach(dao::save);
//
            for (TransportationProduct2 product : products.values()){
                log.info("Remove transportation product " + product.getId());
                dao.remove(product);
            }
            for (TransportationDocument2 document : documents.values()){
                log.info("Remove transportation document " + document.getId());
                dao.remove(document);
            }
            successAnswer.add(CONTRACTS, array);
            JSONObject jsonObject = successAnswer.toJson();
            write(resp, jsonObject.toJSONString());
            pool.put(jsonObject);
        }
    }
}
