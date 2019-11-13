package utils;

import entity.Worker;
import entity.deal.Contract;
import entity.documents.Deal;
import entity.documents.LoadPlan;
import entity.laboratory.SunAnalyses;
import entity.products.Product;
import entity.storages.Storage;
import entity.storages.StorageProduct;
import entity.transport.*;
import entity.weight.Weight;
import org.apache.log4j.Logger;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;
import utils.storages.StorageUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by quasilin on 18.03.2019.
 */
public class TransportUtil{

    private final Logger log = Logger.getLogger(TransportUtil.class);
    static dbDAO dao = dbDAOService.getDAO();
    private static UpdateUtil updateUtil = new UpdateUtil();

    public static void checkTransport(Transportation2 transportation) {
        boolean isArchive = true;
        for (TransportationProduct product : transportation.getProducts()){
            if (product.getWeight().getNetto() == 0){
                isArchive = false;
            }
        }

        if (transportation.getTimeIn() == null) {
            isArchive = false;
        }
        if (transportation.getTimeOut() == null) {
            isArchive = false;
        }

        transportation.setDone(isArchive);
        dao.save(transportation);

        if (isArchive){
            Archivator.add(transportation);
        }
    }
    public static final int HUMIDITY_BASIS = 7;
    public static final int SORENESS_BASIS = 3;
    public static float calculateWeight(TransportationProduct transportation) {
        float percentage = 0;
        boolean newWeight = false;

        SunAnalyses sunAnalyse = null;//transportation.getSunAnalyses();
        if (sunAnalyse != null) {
            float humidity = (sunAnalyse.getHumidity1() + sunAnalyse.getHumidity2()) /
                    ((sunAnalyse.getHumidity1() > 0 ? 1 : 0) + (sunAnalyse.getHumidity2() > 0 ? 1 : 0));
            float soreness = sunAnalyse.getSoreness();

            if (humidity > HUMIDITY_BASIS && soreness > SORENESS_BASIS) {
                percentage = 100 - ((100 - humidity) * (100 - soreness) * 100) / ((100 - HUMIDITY_BASIS) * (100 - SORENESS_BASIS));
            } else if (humidity > HUMIDITY_BASIS) {
                percentage = ((humidity - HUMIDITY_BASIS) * 100) / (100 - HUMIDITY_BASIS);
            } else if (soreness > SORENESS_BASIS) {
                percentage = ((soreness - SORENESS_BASIS) * 100 / (100 - SORENESS_BASIS));
            }

            Weight weight = transportation.getWeight();

            if (weight == null) {
                newWeight = true;
                weight = new Weight();
                weight.setUid(DocumentUIDGenerator.generateUID());
                transportation.setWeight(weight);
            }

            weight.setCorrection(percentage);
            dao.saveWeight(weight);
        }

        if (newWeight){
            dao.save(transportation);
        }
        return percentage;
    }

    public static void archive(LoadPlan loadPlan) throws IOException {
        Transportation transportation = loadPlan.getTransportation();
        transportation.setArchive(true);
        dao.save(transportation);
        updateUtil.onArchive(loadPlan);
    }

    private static final StorageUtil storageUtil = new StorageUtil();
    public synchronized static void updateUsedStorages(TransportationProduct transportation, Worker worker){
//        HashMap<Integer, Float> values = new HashMap<>();
//        float total = 0;
//        for (TransportStorageUsed used : transportation.getUsedStorages()){
//            total += used.getAmount();
//            values.put(used.getId(), used.getAmount());
//        }
//        for (TransportStorageUsed used : transportation.getUsedStorages()){
//            used.setAmount(1f * Math.round(values.get(used.getId()) / total * transportation.getWeight().getNetto() * 100) / 100);
//            updateUsedStorages(transportation, used, worker);
//        }
    }
    public synchronized static void updateUsedStorages(Transportation transportation, TransportStorageUsed tsu, Worker worker) {

        if (tsu.getStorage() == null) {
            Product product = transportation.getProduct();
            List<StorageProduct> storageProducts = dao.getStorageProductByProduct(product);
            Storage storage;
            if (storageProducts.size() > 0) {
                storage = storageProducts.get(0).getStorage();
            } else {
                storage = new Storage();
                storage.setName(product.getName());
                dao.save(storage);
                StorageProduct storageProduct = new StorageProduct();
                storageProduct.setStorage(storage);
                storageProduct.setProduct(product);
                dao.save(storageProduct);
            }
            tsu.setStorage(storage);
        }
        if (tsu.getTransportation() == null) {
            tsu.setTransportation(transportation);
            ActionTime time = new ActionTime(worker);
            tsu.setCreate(time);
            dao.save(time);
        }
        if (tsu.getShipper() == null) {
            tsu.setShipper(transportation.getShipper());
        }

        dao.save(tsu);
        storageUtil.updateStorageEntry(tsu);
    }

    public static Transportation2 newTransportation() {
        Transportation2 transportation = new Transportation2();
        transportation.setUid(DocumentUIDGenerator.generateUID());
        return transportation;
    }

    public static void setDriver(Transportation2 transportation, Driver driver) {
        transportation.setDriver(driver);
        transportation.setDriverLicense(driver.getLicense());
        if(driver.getTruck() == null && transportation.getTruck() != null) {
            driver.setTruck(transportation.getTruck());
            dao.save(driver);
        }
    }

    public static void setTruck(Transportation2 transportation, Truck truck) {

        if (truck != null) {
            transportation.setTruck(truck);
            transportation.setTruckNumber(truck.getNumber());
            transportation.setTransporter(truck.getTransporter());
            if (truck.getTrailer() != null && transportation.getTrailer() == null) {
                setTrailer(transportation, truck.getTrailer());
            } else if (transportation.getTrailer() != null) {
                transportation.setTrailer(null);
                transportation.setTrailerNumber(null);
            }
            if (transportation.getDriver() != null){
                Driver driver = transportation.getDriver();
                if (driver.getTruck() == null){
                    driver.setTruck(truck);
                    dao.save(driver);
                }
            }
        } else {
            transportation.setTruck(null);
            transportation.setTruckNumber(null);
            transportation.setTransporter(null);
        }
    }

    public static void setTrailer(Transportation2 transportation, Trailer trailer) {
        transportation.setTrailer(trailer);
        transportation.setTrailerNumber(trailer.getNumber());
    }
}
