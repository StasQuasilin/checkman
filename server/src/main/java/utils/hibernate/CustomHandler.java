package utils.hibernate;

import entity.DealType;
import entity.Person;
import entity.deal.Contract;
import entity.documents.Shipper;
import entity.products.Product;
import entity.reports.ReportField;
import entity.reports.ReportFieldSettings;
import entity.storages.Storage;
import entity.storages.StoragePeriodPoint;
import entity.storages.StorageProduct;
import entity.transport.*;
import utils.DateUtil;
import utils.U;
import utils.storages.PointScale;
import utils.storages.StorageUtil;
import utils.transport.CollapseUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by szpt_user045 on 01.07.2019.
 */
public class CustomHandler {

    static dbDAO dao = dbDAOService.getDAO();


    public static void main(String[] args) {
        Hibernator instance = Hibernator.getInstance();
        for (Contract contract : dao.getContractsByType(DealType.sell)){
            System.out.println(contract.toJson());
        }
//        for (Vehicle v : instance.query(Vehicle.class, null)){
//            String trailerNumber = v.getTrailerNumber();
//            if (U.exist(trailerNumber)){
//                Trailer trailer = v.getTrailer();
//                if (trailer == null){
//                    trailer = instance.get(Trailer.class, "number", trailerNumber);
//                    if (trailer == null){
//                        trailer = new Trailer();
//                        trailer.setNumber(trailerNumber);
//                        instance.save(trailer);
//                    }
//                    v.setTrailer(trailer);
//                    instance.save(v);
//                }
//            }
//        }
//        for (Transportation t : instance.query(Transportation.class, null)){
//            if (t.getTrailer() == null && t.getVehicle() != null && t.getVehicle().getTrailer() != null){
//                TransportUtil.setVehicle(t, t.getVehicle());
//                instance.save(t);
//            }
//        }
//        List<Driver> drivers = instance.query(Driver.class, null);
//        for (Driver driver : drivers){
//            Vehicle vehicle = driver.getVehicle();
//            if (vehicle != null){
//                instance.save(driver);
//            }
//        }
//        ArrayList<Driver> comparable = new ArrayList<>();
//        for (int i = 0; i < drivers.size(); i++){
//
//            Driver driver = drivers.get(i);
//            Person person = driver.getPerson();
//            comparable.clear();
//            comparable.add(driver);
//            for (int j = 0; j < drivers.size(); j++){
//                Driver d = drivers.get(j);
//                Person p = d.getPerson();
//
//                if (driver.getId() != d.getId()){
//                    boolean compare = true;
//
//                    if(!person.getSurname().equals(p.getSurname())){
//                        compare = false;
//                    }
//                    if (U.exist(person.getForename()) && U.exist(p.getForename()) && person.getForename().substring(0, 1).equals(p.getForename().substring(0, 1))){
//                        compare = false;
//                    }
//                    if (U.exist(person.getPatronymic()) && U.exist(p.getPatronymic()) && person.getPatronymic().substring(0, 1).equals(p.getPatronymic().substring(0, 1))){
//                        compare = false;
//                    }
//
//                    if (compare){
//                        comparable.add(d);
//                        drivers.remove(j);
//                    }
//                }
//            }
//            if (comparable.size() > 1) {
//                CollapseUtil.collapse(comparable);
//            }
//        }

//        for (Transportation transportation : instance.query(Transportation.class, null)){
//            System.out.println(transportation.getDate() + ", " + (transportation.getWeight() != null ? transportation.getWeight().getNetto() : 0));
//        }

//        ProductLoadCalculator calculator = new ProductLoadCalculator();
//        HashMap<String, Object> param = new HashMap<>();
//        for (Product p : instance.query(Product.class, null)){
//            param.put("product", p);
//            calculator.setProduct(p);
////            instance.sum(Transportation.class, param, "weight/brutto")
//            System.out.println(p.getName()+ ": " + calculator.readValue());
//        }

//        for (Deal deal : instance.query(Deal.class, null)){
//            WeightUtil.calculateDealDone(deal);
//        }
//        StorageUtil storageUtil = new StorageUtil();
//        PointScale scale = PointScale.day;
//        PointScale s = scale;
//        while ((s = StorageUtil.nextScale(s)) != scale){
//            scale = s;
//            System.out.println(s);
//        }
//        for (TransportStorageUsed used : instance.query(TransportStorageUsed.class, null)){
//            storageUtil.updateStorageEntry(used);
//        }
//        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
//        ArrayList<StoragePeriodPoint> points = new ArrayList<>();
//
//        List<Shipper> shipperList = dao.getShipperList();
//        for (StorageProduct storageProduct : dao.getObjects(StorageProduct.class)){
//            for (Shipper shipper : shipperList){
//                points.clear();
//                Storage storage = storageProduct.getStorage();
//                Product product = storageProduct.getProduct();
//                storageUtil.getStocks(null, time, storage, product, shipper, PointScale.year, points);
//                for (StoragePeriodPoint point : points){
//                    System.out.println(point.getScale().toString() + ": " + DateUtil.prettyDate(point.getDate()));
//                    System.out.println("\t" + point.getStorage().getName() + ": " + point.getProduct().getName() + ": " + point.getAmount());
//                }
//            }
//        }

//        for (StorageEntry entry : instance.query(StorageEntry.class, null)){
//            storageUtil.dayStock(entry);
//        }

//        Date from = Date.valueOf(LocalDate.of(2019, 10, 21));
//        Date to = Date.valueOf(LocalDate.of(2019, 10, 28));
//        Storage storage = dao.getObjectById(Storage.class, 9);
//        Product product = dao.getObjectById(Product.class, 1);
//        Shipper shipper = dao.getObjectById(Shipper.class, 2);
//
//        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
//        System.out.println(storageUtil.calculateStock(time, storage, product, shipper));
//        for (StoragePeriodPoint point : dao.getStoragePoints(from, to, storage, product, shipper, PointScale.week)){
//            System.out.println(point.getDate());
//        }

//        for (Vehicle vehicle : instance.query(Vehicle.class, null)){
//            String number = vehicle.getNumber();
//            if (U.exist(number)){
//                vehicle.setNumber(Parser.prettyNumber(pretty(number)));
//            }
//            String trailer = vehicle.getTrailer();
//            if (U.exist(trailer)){
//                vehicle.setTrailer(Parser.prettyNumber(pretty(trailer)));
//            }
//            dao.save(vehicle);
//        }

//        String value = "DAF вм1755";
//        System.out.println("Find " + value);
//        List<Vehicle> vehicle = dao.findVehicle(value);
//        for (Vehicle v : vehicle){
//            System.out.println(v);
//        }
        HibernateSessionFactory.shutdown();
    }

    static String pretty(String number){
        StringBuilder builder = new StringBuilder();
        for (char c : number.toCharArray()){
            if (Character.isDigit(c) || Character.isLetter(c)){
                builder.append(c);
            }
        }
        return builder.toString();
    }
}
