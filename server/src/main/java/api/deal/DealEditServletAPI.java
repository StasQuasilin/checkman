package api.deal;

import api.IChangeServletAPI;
import constants.Branches;
import constants.Constants;
import entity.DealType;
import entity.answers.IAnswer;
import entity.deal.Contract;
import entity.deal.ContractProduct;
import entity.documents.Shipper;
import entity.products.Product;
import entity.Worker;
import entity.documents.Deal;
import entity.log.comparators.DealComparator;
import entity.organisations.Organisation;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.*;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.DEAL_SAVE)
public class DealEditServletAPI extends IChangeServletAPI {

    private static final String DEAL_PRODUCTS = "dealProducts";
    private final DealComparator comparator = new DealComparator();
    private final Logger log = Logger.getLogger(DealEditServletAPI.class);
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body);
            Contract contract;
            Worker worker = getWorker(req);
            boolean save = false;

            long id = -1;

            if (body.containsKey(ID)) {
                id = Long.parseLong(String.valueOf(body.get(Constants.ID)));
            }
            if (id != -1) {
                contract = dao.getObjectById(Contract.class, id);
            } else {
                contract = new Contract();
                contract.setManager(worker);
                contract.setCreateTime(new ActionTime(worker));
                save = true;
            }

            Date from = Date.valueOf(String.valueOf(body.get(Constants.DATE)));
            Date to = Date.valueOf(String.valueOf(body.get(Constants.DATE_TO)));
            if (from.after(to)) {
                Date temp = from;
                from = to;
                to = temp;
            }

            if (contract.getFrom() == null || !contract.getFrom().equals(from)) {
                contract.setFrom(from);
                save = true;
            }

            if (contract.getTo() == null || !contract.getTo().equals(to)) {
                contract.setTo(to);
                save = true;
            }

            long organisationId = (long) body.get(Constants.COUNTERPARTY);
            if (contract.getCounterparty() == null || contract.getCounterparty().getId() != organisationId){
                contract.setCounterparty(dao.getObjectById(Organisation.class, organisationId));
                save = true;
            }

            if(body.containsKey(NUMBER)){
                String number = String.valueOf(body.get(NUMBER));
                contract.setNumber(number);
            }

            HashMap<Integer, ContractProduct> products = new HashMap<>();
            for (ContractProduct product : contract.getProducts()){
                products.put(product.getId(), product);
            }

            ArrayList<ContractProduct> newProducts = new ArrayList<>();

            for (Object o : (JSONArray)body.get(DEAL_PRODUCTS)){
                JSONObject p = (JSONObject) o;
                ContractProduct contractProduct = null;
                if (p.containsKey(ID)){
                    int contractProductId = Integer.parseInt(String.valueOf(p.get(ID)));
                    contractProduct = products.remove(contractProductId);
                }
                if (contractProduct == null){
                    contractProduct = new ContractProduct();
                    contractProduct.setContract(contract);
                }

                newProducts.add(contractProduct);

                DealType type = DealType.valueOf(String.valueOf(p.get(TYPE)));
                contractProduct.setType(type);

                Product product = dao.getObjectById(Product.class, p.get(PRODUCT));
                contractProduct.setProduct(product);

                Shipper shipper = dao.getObjectById(Shipper.class, p.get(SHIPPER));
                contractProduct.setShipper(shipper);

                contractProduct.setAmount(Float.parseFloat(String.valueOf(p.get(AMOUNT))));
                contractProduct.setPrice(Float.parseFloat(String.valueOf(p.get(PRICE))));

            }
            write(resp, SUCCESS_ANSWER);

            if (save) {
                dao.save(contract.getCreateTime());
                dao.save(contract);
                updateUtil.onSave(contract);
                products.values().forEach(dao::remove);
                newProducts.forEach(dao::save);

            } else {
                write(resp, SUCCESS_ANSWER);
            }

            body.clear();
            products.clear();
            newProducts.clear();
        } else {
            write(resp, EMPTY_BODY);
        }

    }
}
