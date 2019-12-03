package api.retail;

import api.ServletAPI;
import constants.Branches;
import entity.Address;
import entity.DealType;
import entity.Worker;
import entity.deal.Contract;
import entity.deal.ContractProduct;
import entity.documents.Shipper;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.transport.ActionTime;
import entity.weight.Unit;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import utils.ContractUtil;
import utils.DateUtil;
import utils.DocumentUIDGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 03.12.2019.
 */
@WebServlet(Branches.API.RETAIL_EDIT)
public class SaveRetailServletAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(SaveRetailServletAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Worker worker = getWorker(req);
            Date date = DateUtil.parseFromEditor(String.valueOf(body.get(DATE)));
            log.info("Date: " + date.toString());

            for (Object d : (JSONArray) body.get(DEALS)){
                JSONObject deal = (JSONObject) d;
                boolean saveContract = false;
                Contract contract = dao.getObjectById(Contract.class, deal.get(ID));
                if (contract == null){
                    log.info("New contract");
                    contract = new Contract();
                    contract.setManager(worker);
                    contract.setUid(DocumentUIDGenerator.generateUID());
                    contract.setCreateTime(new ActionTime(worker));
                } else {
                    log.info("Contract #" + contract.getId());
                }

                if (ContractUtil.setFrom(contract, date)){
                    saveContract = true;
                }
                if (ContractUtil.setTo(contract, date)){
                    saveContract = true;
                }
                Address address = dao.getObjectById(Address.class, deal.get(ADDRESS));
                if (ContractUtil.setAddress(contract, address)){
                    saveContract = true;
                }

                contract.setTo(date);
                Organisation organisation = dao.getObjectById(Organisation.class, deal.get(COUNTERPARTY));
                log.info("Counterparty: " + organisation.getValue());
                contract.setCounterparty(organisation);

                HashMap<Integer, ContractProduct> products = new HashMap<>();
                for (ContractProduct p : contract.getProducts()){
                    products.put(p.getId(), p);
                }
                ArrayList<ContractProduct> keepItProducts = new ArrayList<>();

                for (Object p : (JSONArray)deal.get(PRODUCTS)){
                    JSONObject p1 = (JSONObject) p;
                    int productId = -1;
                    if (p1.containsKey(ID)){
                        productId = Integer.parseInt(String.valueOf(p1.get(ID)));
                    }
                    ContractProduct contractProduct;
                    if (products.containsKey(productId)){
                        contractProduct = products.remove(productId);
                    } else {
                        contractProduct = new ContractProduct();
                        contractProduct.setContract(contract);
                    }

                    boolean saveThisProduct = false;

                    DealType type = DealType.valueOf(String.valueOf(p1.get(TYPE)));
                    if (ContractUtil.setType(contractProduct, type)){
                        saveThisProduct = true;
                    }

                    Product product = dao.getObjectById(Product.class, p1.get(PRODUCT));
                    if (ContractUtil.setProduct(contractProduct, product)){
                        saveThisProduct = true;
                    }
                    Float amount = Float.parseFloat(String.valueOf(p1.get(AMOUNT)));
                    if (ContractUtil.setAmount(contractProduct, amount)){
                        saveThisProduct = true;
                    }

                    Unit unit = dao.getObjectById(Unit.class, p1.get(UNIT));
                    if (ContractUtil.setUnit(contractProduct, unit)){
                        saveThisProduct = true;
                    }

                    Float price = Float.parseFloat(String.valueOf(p1.get(PRICE)));
                    if (ContractUtil.setPrice(contractProduct, price)){
                        saveThisProduct = true;
                    }
                    Shipper shipper = dao.getObjectById(Shipper.class, p1.get(SHIPPER));
                    if(ContractUtil.setShipper(contractProduct, shipper)){
                        saveThisProduct = true;
                    }
                    if (saveThisProduct) {
                        keepItProducts.add(contractProduct);
                    }
                }
                if (saveContract || keepItProducts.size() > 0){
                    dao.save(contract.getCreateTime(), contract);
                }
                keepItProducts.forEach(dao::save);
                products.values().forEach(dao::remove);

                keepItProducts.clear();
                products.clear();

            }

            write(resp, SUCCESS_ANSWER);
        }
    }
}
