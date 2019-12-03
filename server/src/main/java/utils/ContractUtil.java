package utils;

import entity.Address;
import entity.DealType;
import entity.deal.Contract;
import entity.deal.ContractProduct;
import entity.documents.Shipper;
import entity.products.Product;
import entity.weight.Unit;

import java.sql.Date;

/**
 * Created by szpt_user045 on 03.12.2019.
 */
public class ContractUtil {
    public static boolean setProduct(ContractProduct contractProduct, Product product) {
        if (contractProduct.getProduct() == null || contractProduct.getProduct().getId() != product.getId()){
            contractProduct.setProduct(product);
            return true;
        }
        return false;
    }

    public static boolean setAmount(ContractProduct contractProduct, float amount) {
        if (contractProduct.getAmount() != amount){
            contractProduct.setAmount(amount);
            return true;
        }
        return false;
    }

    public static boolean setPrice(ContractProduct contractProduct, float price) {
        if (contractProduct.getPrice() != price){
            contractProduct.setPrice(price);
            return true;
        }
        return false;
    }

    public static boolean setShipper(ContractProduct contractProduct, Shipper shipper) {
        if (contractProduct.getShipper() ==null || contractProduct.getShipper().getId() != shipper.getId()){
            contractProduct.setShipper(shipper);
            return true;
        }
        return false;
    }

    public static boolean setFrom(Contract contract, Date date) {
        if (contract.getFrom() == null || !contract.getFrom().equals(date)){
            contract.setFrom(date);
            return true;
        }
        return false;
    }

    public static boolean setTo(Contract contract, Date date) {
        if (contract.getTo() == null || !contract.getTo().equals(date)){
            contract.setTo(date);
            return true;
        }
        return false;
    }

    public static boolean setAddress(Contract contract, Address address) {
        if (contract.getAddress() == null || contract.getAddress().getId() != address.getId()){
            contract.setAddress(address);
            return true;
        }
        return false;
    }

    public static boolean setType(ContractProduct contractProduct, DealType type) {
        if (contractProduct.getType() != type){
            contractProduct.setType(type);
            return true;
        }
        return false;
    }

    public static boolean setUnit(ContractProduct contractProduct, Unit unit) {
        if (contractProduct.getUnit() == null || contractProduct.getUnit().getId() != unit.getId()){
            contractProduct.setUnit(unit);
            return true;
        }
        return false;
    }
}
