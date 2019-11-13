package utils.hibernate;

import entity.transport.*;

/**
 * Created by szpt_user045 on 01.07.2019.
 */
public class CustomHandler {

//    static dbDAO dao = dbDAOService.getDAO();


    public static void main(String[] args) {
        Hibernator instance = Hibernator.getInstance();
//        for (VehicleNumber number : instance.query(VehicleNumber.class, null)){
//            System.out.println(number);
//        }
        for (Vehicle vehicle : instance.query(Vehicle.class, null)){
            Truck truck = new Truck();
            truck.setModel(vehicle.getModel());
            truck.setNumber(vehicle.getNumber());

            if (vehicle.getTrailer() != null){
                Trailer trailer = new Trailer();
                trailer.setNumber(vehicle.getTrailer());
                instance.save(trailer);
                truck.setTrailer(trailer);
            }

            instance.save(truck);
        }

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
