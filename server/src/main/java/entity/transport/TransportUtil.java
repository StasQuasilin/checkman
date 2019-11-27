package entity.transport;

import entity.Worker;
import entity.documents.Deal;
import entity.documents.LoadPlan;
import entity.laboratory.SunAnalyses;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.storages.Storage;
import entity.storages.StorageProduct;
import entity.weight.Weight;
import org.apache.log4j.Logger;
import utils.Archivator;
import utils.DocumentUIDGenerator;
import utils.UpdateUtil;
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

    public static void checkTransport(Transportation transportation) {
        boolean isArchive = true;
        if (transportation.getWeight() == null || transportation.getWeight().getNetto() == 0){
            isArchive = false;
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
            try {
                updateUtil.onSave(transportation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static final int HUMIDITY_BASIS = 7;
    public static final int SORENESS_BASIS = 3;
    public static float calculateWeight(Transportation transportation) {
        float percentage = 0;

        SunAnalyses sunAnalyse = transportation.getSunAnalyses();
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
        }
        Weight weight = transportation.getWeight();
        boolean newWeight = false;
        if (weight == null) {
            newWeight = true;
            weight = new Weight();
            weight.setUid(DocumentUIDGenerator.generateUID());
            transportation.setWeight(weight);
        }

        weight.setCorrection(percentage);

        dao.saveWeight(weight);
        if (newWeight){
            dao.saveTransportation(transportation);
        }
        return percentage;
    }

    public static void archive(LoadPlan loadPlan) throws IOException {
        Transportation transportation = loadPlan.getTransportation();
        transportation.setArchive(true);
        dao.save(transportation);
        updateUtil.onArchive(loadPlan);
    }

    public synchronized static Transportation createTransportation(Deal deal, Worker manager, Worker creator) {
        Transportation transportation = new Transportation();

        transportation.setUid(DocumentUIDGenerator.generateUID());
        transportation.setProduct(deal.getProduct());
        transportation.setType(deal.getType());
        transportation.setCounterparty(deal.getOrganisation());
        transportation.setManager(manager);

        transportation.setCreator(creator);
        transportation.setShipper(deal.getShipper());

        return transportation;
    }

    private static final StorageUtil storageUtil = new StorageUtil();
    public synchronized static void updateUsedStorages(Transportation transportation, Worker worker){
        HashMap<Integer, Float> values = new HashMap<>();
        float total = 0;
        for (TransportStorageUsed used : transportation.getUsedStorages()){
            total += used.getAmount();
            values.put(used.getId(), used.getAmount());
        }
        for (TransportStorageUsed used : transportation.getUsedStorages()){
            used.setAmount(1f * Math.round(values.get(used.getId()) / total * transportation.getWeight().getNetto() * 100) / 100);
            updateUsedStorages(transportation, used, worker);
        }
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

    public static void setVehicle(Transportation transportation, Vehicle vehicle) {
        if (vehicle != null) {
            transportation.setVehicle(vehicle);
            transportation.setTruckNumber(vehicle.getNumber());
            if (vehicle.getTrailer() != null && transportation.getTrailer() == null) {
                setTrailer(transportation, vehicle.getTrailer());
            }
            Driver driver = transportation.getDriver();
            if (driver != null) {
                if (driver.getVehicle() == null) {
                    driver.setVehicle(vehicle);
                    dao.save(driver);
                }
            }
        } else{
            transportation.setVehicle(null);
            transportation.setTruckNumber(null);
        }
    }

    public static void setTrailer(Transportation transportation, Trailer trailer) {
        if (trailer != null){
            transportation.setTrailer(trailer);
            transportation.setTrailerNumber(trailer.getNumber());
        } else {
            transportation.setTrailer(null);
            transportation.setTrailerNumber(null);
        }
    }

    public static void setDriver(Transportation transportation, Driver driver) {
        if(driver != null) {
            transportation.setDriver(driver);
            transportation.setDriverLicense(driver.getLicense());
            if (driver.getVehicle() != null && transportation.getVehicle() == null) {
                setVehicle(transportation, driver.getVehicle());
            } else if (transportation.getVehicle() != null){
                driver.setVehicle(transportation.getVehicle());
                dao.save(driver);
            }
            if (driver.getOrganisation() != null && transportation.getTransporter() == null){
                setTransporter(transportation, driver.getOrganisation());
            }
        } else {
            transportation.setDriver(null);
            transportation.setDriverLicense(null);
        }
    }

    public static void setTransporter(Transportation transportation, Organisation transporter){
        transportation.setTransporter(transporter);
        if (transporter != null) {
            transportation.setTransporterValue(transporter.getValue());
            Driver driver = transportation.getDriver();
            if (driver != null) {
                if (driver.getOrganisation() == null) {
                    driver.setOrganisation(transporter);
                    dao.save(driver);
                }
            }
        } else {
            transportation.setTransporter(null);
        }
    }
}
