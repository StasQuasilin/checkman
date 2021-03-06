package utils.contracts;

import constants.Constants;
import entity.organisations.Address;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.ContractUtil;
import utils.DateUtil;
import utils.DocumentUIDGenerator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 04.12.2019.
 */
public class ContractSaver implements Constants{

    private final dbDAO dao = dbDAOService.getDAO();
    private final Logger log = Logger.getLogger(ContractSaver.class);

    public synchronized Contract saveContract(JSONObject deal, Worker worker) {
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

        Date from = DateUtil.parseFromEditor(String.valueOf(deal.get(FROM)));
        if (ContractUtil.setFrom(contract, from)){
            saveContract = true;
        }
        Date to = DateUtil.parseFromEditor(String.valueOf(deal.get(TO)));
        if (ContractUtil.setTo(contract, to)){
            saveContract = true;
        }

        Address address = dao.getObjectById(Address.class, deal.get(ADDRESS));
        if (ContractUtil.setAddress(contract, address)){
            saveContract = true;
        }

        Organisation organisation = dao.getObjectById(Organisation.class, deal.get(COUNTERPARTY));
        log.info("Counterparty: " + organisation.getValue());
        contract.setCounterparty(organisation);

        HashMap<Integer, ContractProduct> products = new HashMap<>();
        for (ContractProduct p : contract.getProducts()){
            products.put(p.getId(), p);
        }
        ArrayList<ContractProduct> actualProducts = new ArrayList<>();
        ArrayList<ContractProduct> keepItProducts = new ArrayList<>();

        for (Object o : (JSONArray)deal.get(PRODUCTS)){
            JSONObject p = (JSONObject) o;
            int productId = -1;
            if (p.containsKey(ID)){
                productId = Integer.parseInt(String.valueOf(p.get(ID)));
            }

            ContractProduct contractProduct;
            if (products.containsKey(productId)){
                contractProduct = products.remove(productId);
                log.info("\tEdit contract product " + contractProduct.getId());
            } else {
                contractProduct = new ContractProduct();
                contractProduct.setContract(contract);
                log.info("\tEdit new contract product");
            }

            boolean saveThisProduct = false;

            DealType type = DealType.valueOf(String.valueOf(p.get(TYPE)));
            if (ContractUtil.setType(contractProduct, type)){
                saveThisProduct = true;
            }

            Product product = dao.getObjectById(Product.class, p.get(PRODUCT));
            if (ContractUtil.setProduct(contractProduct, product)){
                saveThisProduct = true;
            }
            Float amount = Float.parseFloat(String.valueOf(p.get(AMOUNT)));
            if (ContractUtil.setAmount(contractProduct, amount)){
                saveThisProduct = true;
            }

            Unit unit = dao.getObjectById(Unit.class, p.get(UNIT));
            if (ContractUtil.setUnit(contractProduct, unit)){
                saveThisProduct = true;
            }

            Float price = Float.parseFloat(String.valueOf(p.get(PRICE)));
            if (ContractUtil.setPrice(contractProduct, price)){
                saveThisProduct = true;
            }
            Shipper shipper = dao.getObjectById(Shipper.class, p.get(SHIPPER));
            if(ContractUtil.setShipper(contractProduct, shipper)){
                saveThisProduct = true;
            }
            if (saveThisProduct) {
                keepItProducts.add(contractProduct);
            }
            actualProducts.add(contractProduct);
        }
        if (saveContract || keepItProducts.size() > 0){
            log.info("Save contract " + contract.getId());
            dao.save(contract.getCreateTime(), contract);
        }

        for (ContractProduct product : keepItProducts){
            if (product.getId() > 0){
                log.info("Save product " + product.getId());
            } else {
                log.info("Save new product");
            }

        }
        keepItProducts.forEach(dao::save);
        for (ContractProduct product : products.values()){
            log.info("Remove product " + product.getId());
            dao.remove(product);
        }

        contract.getProducts().clear();
        contract.getProducts().addAll(actualProducts);

        keepItProducts.clear();
        products.clear();
        actualProducts.clear();
        return contract;
    }
}
