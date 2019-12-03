package api.retail;

import api.ServletAPI;
import com.google.gson.JsonArray;
import constants.Branches;
import controllers.weight.printing.DocumentGenerator;
import entity.Address;
import entity.Worker;
import entity.deal.Contract;
import entity.deal.ContractProduct;
import entity.transport.*;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import utils.DateUtil;
import utils.DocumentUIDGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by szpt_user045 on 02.12.2019.
 */
@WebServlet(Branches.API.RETAIL_EDIT)
public class SaveRetailTransportationServlet extends ServletAPI {

    private final Logger log = Logger.getLogger(SaveRetailTransportationServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Date date = DateUtil.parseFromEditor(String.valueOf(body.get(DATE)));
            log.info("Date: " + date.toString());
            Worker worker = getWorker(req);
            Transportation2 transportation = dao.getObjectById(Transportation2.class, body.get(ID));

            if (transportation == null) {
                log.info("New transportation...");
                transportation = new Transportation2();
                transportation.setUid(DocumentUIDGenerator.generateUID());
                transportation.setCreateTime(new ActionTime(worker));
                transportation.setManager(worker);
            } else {
                log.info("Transportation #" + transportation.getId());
            }

            if (transportation.getDate() == null || !transportation.getDate().equals(date)){
                transportation.setDate(date);
            }

            Driver driver = dao.getObjectById(Driver.class, body.get(DRIVER));
            TransportUtil.setDriver(transportation, driver);

            Vehicle vehicle = dao.getObjectById(Vehicle.class, body.get(VEHICLE));
            TransportUtil.setVehicle(transportation, vehicle);

            for (Object d : (JSONArray) body.get(DEALS)){
                JSONObject deal = (JSONObject) d;
                Contract contract = dao.getObjectById(Contract.class, deal.get(ID));
                if (contract == null){
                    log.info("New contract");
                    contract = new Contract();
                } else {
                    log.info("Contract #" + contract.getId());
                }

                contract.setManager(worker);
                contract.setFrom(date);
                contract.setTo(date);
                //todo add counterparty

                Address address = dao.getObjectById(Address.class, deal.get(ADDRESS));
                log.info("Address: " + address.getValue());

                TransportationDocument document = new TransportationDocument();
                document.setTransportation(transportation);
                document.setAddress(address);

                for (Object p : (JSONArray)deal.get(PRODUCTS)){
                    JSONObject product = (JSONObject) p;
                    ContractProduct contractProduct = dao.getObjectById(ContractProduct.class, product.get(ID));
                    if (contractProduct == null){
                        contractProduct = new ContractProduct();
                        contractProduct.setContract(contract);
                    }
                    TransportationProduct transportationProduct = new TransportationProduct();
                    transportationProduct.setContractProduct(contractProduct);
                    transportationProduct.setDocument(document);
                }

            }
//            Transportation2
//            TransportationDocument
//            TransportationProduct
            write(resp, SUCCESS_ANSWER);
        }
    }
}
