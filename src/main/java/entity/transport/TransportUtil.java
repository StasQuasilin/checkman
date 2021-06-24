package entity.transport;

import api.deal.DealEditor;
import entity.Worker;
import entity.documents.Deal;
import entity.laboratory.SunAnalyses;
import entity.notifications.Notification;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.storages.Storage;
import entity.storages.StorageProduct;
import entity.weight.Weight;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.*;
import utils.hibernate.Hibernator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;
import utils.notifications.SomeNotificator;
import utils.storages.StatisticUtil;
import utils.storages.StorageUtil;
import utils.transport.TransportationEditor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static constants.Constants.DEAL;
import static constants.Constants.PRODUCT;
import static utils.hibernate.State.notNull;

/**
 * Created by quasilin on 18.03.2019.
 */
public class TransportUtil{

    private static final JsonPool pool = JsonPool.getPool();
    private static final Logger log = Logger.getLogger(TransportUtil.class);
    static dbDAO dao = dbDAOService.getDAO();
    private static final UpdateUtil updateUtil = new UpdateUtil();
    private static final SomeNotificator notificator = new SomeNotificator();
    private static final LanguageBase base = LanguageBase.getBase();
    public static final String SUCCESS_TEXT = "notificator.archived.success";

    public static void checkTransport(Transportation transportation) {
        boolean isDone = true;

        for (TransportationProduct product : transportation.getProducts()){
            final Weight weight = product.getWeight();
            if (weight == null || weight.getNetto() == 0){
                isDone = false;
            }
        }

        if (transportation.getTimeIn() == null) {
            isDone = false;
        }
        if (transportation.getTimeOut() == null) {
            isDone = false;
        }

        boolean update = false;
        if (transportation.isDone() != isDone){
            transportation.setDone(isDone);
            dao.save(transportation);
            update = true;
        }

        if (update){
            updateUtil.onSave(transportation);
        }
    }
    public static final int HUMIDITY_BASIS = 7;
    public static final int SORENESS_BASIS = 3;
    public static void calculateWeight(TransportationProduct transportation) {

        Weight weight = transportation.getWeight();
        if (weight != null) {
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
            weight.setCorrection(percentage);
        }

    }

    public static void archive(Transportation transportation, Worker worker) throws IOException {
        log.info(worker.getValue() + " archive transportation " + transportation.getId());
        transportation.setArchive(true);
        dao.save(transportation);
        updateUtil.onRemove(transportation);
        JSONObject json = new Notification(
                String.format(
                        base.get(worker.getLanguage(), SUCCESS_TEXT),
                        transportation.getDriver().getPerson().getValue(),
                        transportation.getCounterparty().getValue(),
                        transportation.getProduct().getName(),
                        worker.getPerson().getValue())
        ).toJson();
        notificator.sendNotification(json);
        pool.put(json);
    }

    public synchronized static Transportation createTransportation(Worker manager, Worker creator) {
        Transportation transportation = new Transportation();
        transportation.setUid(DocumentUIDGenerator.generateUID());
        transportation.setManager(manager);
        transportation.setCreateTime(new ActionTime(creator));
        return transportation;
    }

    private static final StorageUtil storageUtil = new StorageUtil();
    private static final StatisticUtil statisticUtil = new StatisticUtil();

    public synchronized static void updateUsedStorages(Transportation t, List<TransportStorageUsed> u, Worker worker){
        HashMap<Integer, Float> values = new HashMap<>();
        float total = 0;
        for (TransportStorageUsed used : u){
            total += used.getAmount();
            values.put(used.getId(), used.getAmount());
        }
        for (TransportStorageUsed used : u){
            used.setAmount(1f * Math.round(values.get(used.getId()) / total * t.getWeight().getNetto() * 100) / 100);
            updateUsedStorages(t, used, worker);
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
            final Trailer t = vehicle.getTrailer();
            final Trailer trailer = transportation.getTrailer();
            if (trailer == null && t != null){
                setTrailer(transportation, t);
            } else {
                vehicle.setTrailer(transportation.getTrailer());
                dao.save(vehicle);
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
            Vehicle vehicle = transportation.getVehicle();
            if (vehicle != null) {
                if (vehicle.getTrailer() != null && !U.exist(vehicle.getTrailer().getNumber())) {
                    Trailer remove = vehicle.getTrailer();
                    vehicle.setTrailer(null);
                    dao.save(vehicle);
                    dao.remove(remove);
                }
                if (vehicle.getTrailer() == null || !U.exist(vehicle.getTrailer().getNumber())) {
                    vehicle.setTrailer(trailer);
                    dao.save(vehicle);
                }
            }
        } else {
            transportation.setTrailer(null);
            transportation.setTrailerNumber(null);
        }
    }

    public static void setDriver(Transportation transportation, Driver driver, boolean b) {
        if(driver != null) {
            transportation.setDriver(driver);
            transportation.setDriverLicense(driver.getLicense());

            final Vehicle vehicle = driver.getVehicle();
            final Vehicle v = transportation.getVehicle();
            if (v == null && vehicle != null){
                setVehicle(transportation, vehicle);
            } else if (v != null){
                driver.setVehicle(v);
                dao.save(driver);
            }
            if (b) {
                final Organisation organisation = driver.getOrganisation();
                final Organisation transporter = transportation.getTransporter();
                if (organisation != null && transporter == null) {
                    setTransporter(transportation, organisation);
                }
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

    public static void updateUnloadStatistic(Transportation transportation) {
        statisticUtil.updateStatisticEntry(transportation);
    }


}
