package utils.storages;

import entity.documents.Shipper;
import entity.products.Product;
import entity.storages.*;
import org.apache.log4j.Logger;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 08.10.2019.
 */
public class StorageUtil {

    private static final Logger log = Logger.getLogger(StorageUtil.class);
    private final dbDAO dao = dbDAOService.getDAO();
    private StorageStocks storageStocks = StorageStocks.getInstance();

    public StorageUtil() {
        if (!isInit) {
            init();
        }
    }

    public void removeStorageEntry(StorageDocument document){
        int documentId = document.getId();
        StorageDocumentType documentType = document.getType();
        log.info("Remove storage entry for " + documentId + ", " + documentType.toString());
        StorageEntry entry = dao.getStorageEntry(documentId, documentType);
        if (entry != null) {
            dao.remove(entry);
            dayStock(entry);
        }
    }

    public void updateStorageEntry(StorageDocument document){
        int documentId = document.getId();
        StorageDocumentType documentType = document.getType();
        StorageEntry entry = dao.getStorageEntry(documentId, documentType);

        boolean save = false;
        if (entry == null){
            entry = new StorageEntry();
            entry.setDocument(documentId);
            entry.setType(documentType);
            save = true;
        }

        Timestamp time = document.getDate();
        if (entry.getTime() == null || !entry.getTime().equals(time)){
            entry.setTime(time);
            save = true;
        }

        int prevStorageId = -1;
        Storage storage = document.getStorage();
        if (entry.getStorage() == null){
            entry.setStorage(storage);
            save = true;
        } else if (entry.getStorage().getId() != storage.getId()){
            prevStorageId = entry.getStorage().getId();
            entry.setStorage(storage);
            save = true;
        }

        int prevProductId = -1;
        Product product = document.getProduct();
        if (entry.getProduct() == null){
            entry.setProduct(product);
            save = true;
        } else if (entry.getProduct().getId() != product.getId()){
            prevProductId = entry.getProduct().getId();
            entry.setProduct(product);
            save = true;
        }

        int prevShipperId = -1;
        Shipper shipper = document.getShipper();
        if (entry.getShipper() == null){
            entry.setShipper(shipper);
            save = true;
        } else if(entry.getShipper().getId() != shipper.getId()){
            prevShipperId = entry.getShipper().getId();
            entry.setShipper(shipper);
            save = true;
        }

        float amount = document.getQuantity();
        if (entry.getAmount() != amount){
            entry.setAmount(amount);
            save = true;
        }

        if (save){
            dao.save(entry);
            dayStock(entry);

            Storage prevStorage;
            if (prevStorageId != -1){
                prevStorage = dao.getStorageById(prevStorageId);
            } else {
                prevStorage = entry.getStorage();
            }

            Product prevProduct;
            if (prevProductId != -1){
                prevProduct = dao.getProductById(prevProductId);
            } else {
                prevProduct = entry.getProduct();
            }

            Shipper prevShipper;
            if (prevShipperId != -1){
                prevShipper = dao.getShipperById(prevShipperId);
            } else {
                prevShipper = entry.getShipper();
            }

            dayStock(entry.getTime(), prevStorage, prevProduct, prevShipper);
        }
    }

    public void dayStock(StorageEntry entry){
        dayStock(entry.getTime(), entry.getStorage(), entry.getProduct(), entry.getShipper());
    }
    private void dayStock(Timestamp time, Storage storage, Product product, Shipper shipper){
        LocalDate date = time.toLocalDateTime().toLocalDate();
        Date _date = Date.valueOf(date);

        float plusAmount = 0;
        float minusAmount = 0;

        for (StorageEntry entry : dao.getStorageEntries(_date, Date.valueOf(date.plusDays(1)), storage, product, shipper)){
            float amount = entry.getAmount();
            if (amount > 0){
                plusAmount += amount;
            } else {
                minusAmount += amount;
            }
        }
        log.info("Update day stock " + date);
        if (plusAmount != 0) {
            updateStock(_date, storage, product, shipper, PointScale.day, plusAmount);
        }
        if (minusAmount != 0){
            updateStock(_date, storage, product, shipper, PointScale.day, minusAmount);
        }
        updateStockByStock(time, storage, product, shipper, PointScale.week);
        calculateStock(Timestamp.valueOf(LocalDateTime.now()), storage, product, shipper);
    }

    private void updateStockByStock(Timestamp time, Storage storage, Product product, Shipper shipper, PointScale scale) {
        LocalDate date = time.toLocalDateTime().toLocalDate();
        Date beginDate = Date.valueOf(getBeginDate(date, scale));
        Date endDate = Date.valueOf(getEndDate(date, scale));
        float plusAmount = 0;
        float minusAmount = 0;
        for (StoragePeriodPoint point : dao.getStoragePoints(beginDate, endDate, storage, product, shipper, prevScale(scale))){
            float amount = point.getAmount();
            if (amount > 0){
                plusAmount += amount;
            } else {
                minusAmount += amount;
            }
        }
        if (plusAmount != 0) {
            updateStock(beginDate, storage, product, shipper, scale, plusAmount);
        }
        if (minusAmount != 0){
            updateStock(beginDate, storage, product, shipper, scale, minusAmount);
        }
        PointScale nextScale = nextScale(scale);
        if (nextScale != scale){
            updateStockByStock(time, storage, product, shipper, nextScale);
        }
    }

    Timestamp now = Timestamp.valueOf(LocalDateTime.now());
    private static boolean isInit = false;
    public void init(){
        isInit = true;
        List<Shipper> shippers = dao.getShipperList();

        for (Storage storage : dao.getStorages()) {
            for (StorageProduct storageProduct : dao.getStorageProductByStorage(storage)) {
                Product product = storageProduct.getProduct();
                for (Shipper shipper : shippers){
                    calculateStock(now, storage, product, shipper);
                }
            }
        }
    }

    public HashMap<Storage, HashMap<Product, HashMap<Shipper, Float>>> getStocks() {
        return storageStocks.stocks;
    }

    private void updateStock(Date date, Storage storage, Product product, Shipper shipper, PointScale scale, float amount){
        log.info(storage.getName() + ": " + product.getName() + ": " + shipper.getValue() + " by " + scale.toString() + "=" + amount);
        StoragePeriodPoint point = null;
        for (StoragePeriodPoint spp : dao.getStoragePoints(date, date, storage, product, shipper, scale)){
            if (spp.getAmount() != 0 && Math.signum(spp.getAmount()) == Math.signum(amount)){
                point = spp;
            } else if (spp.getAmount() == 0){
                dao.remove(spp);
            }
        }
        if (point == null) {
            log.info("No such point");
            point = new StoragePeriodPoint();
            point.setDate(date);
            point.setScale(scale);
            point.setStorage(storage);
            point.setProduct(product);
            point.setShipper(shipper);
        }

        if (point.getAmount() != amount){
            point.setAmount(amount);
            if (point.getAmount() == 0){
                dao.remove(point);
            } else {
                dao.save(point);
            }
        }
    }

    public float calculateStock(Timestamp time, Storage storage, Product product, Shipper shipper){
        ArrayList<StoragePeriodPoint> points = new ArrayList<>();

        pureStocks(null, time, storage, product, shipper, PointScale.year, points);

        float stocks = 0;
        for (StoragePeriodPoint point : points){
            stocks += point.getAmount();
        }
        storageStocks.updateStock(time, storage, product, shipper, stocks);
        points.clear();
        return stocks;
    }

    public synchronized void pureStocks(Date prev, Timestamp time, Storage storage, Product product, Shipper shipper, PointScale scale, ArrayList<StoragePeriodPoint> points){
        getStocks(prev, time, storage, product, shipper, scale, points);
        ArrayList<StoragePeriodPoint> temp = new ArrayList<>(points);
        for (StoragePeriodPoint point : temp){
            PointScale pointScale = nextScale(point.getScale());
            if (pointScale != point.getScale()) {
                points.remove(point);
            }
        }
        temp.clear();
    }

    public synchronized void getStocks(Date prev, Timestamp time, Storage storage, Product product, Shipper shipper, PointScale scale, ArrayList<StoragePeriodPoint> points){
        LocalDate localDate = getBeginDate(time.toLocalDateTime().toLocalDate(), scale);
        Date date = Date.valueOf(localDate);

        points.addAll(dao.getStoragePoints(prev, date, storage, product, shipper, scale));

        PointScale s = prevScale(scale);
        if (s != scale){
            getStocks(date, time, storage, product, shipper, s, points);
        }
    }

    public static synchronized LocalDate getBeginDate(LocalDate date, PointScale scale){
        int minus;
        switch (scale){
            case week:
                minus = Math.min(toBegin(date, scale), toBegin(date, PointScale.month));
                break;
            default:
                minus = toBegin(date, scale);
        }

        return date.minusDays(minus);
    }

    public static synchronized LocalDate getEndDate(LocalDate date, PointScale scale){
        int plus;
        switch (scale){
            case week:
                plus = Math.min(toEnd(date, scale), toEnd(date, PointScale.month));
                break;
            default:
                plus = toEnd(date, scale);
        }
        return date.plusDays(plus);
    }

    public static int toBegin(LocalDate date, PointScale scale){
        switch (scale){
            case day:
                return 0;
            case week:
                return date.getDayOfWeek().getValue() - 1;
            case month:
                return date.getDayOfMonth() - 1;
            case year:
                return date.getDayOfYear() - 1;
            default:
                log.warn("No such action for scale " + scale.toString());
                return 0;
        }
    }

    public static int toEnd(LocalDate date, PointScale scale){
        switch (scale){
            case day:
                return 0;
            case week:
                return 7 - date.getDayOfWeek().getValue();
            case month:
                return date.lengthOfMonth() - date.getDayOfMonth();
            case year:
                return date.lengthOfYear() - date.getDayOfYear();
            default:
                log.warn("No such action for scale " + scale.toString());
                return 0;
        }
    }

    public static PointScale prevScale(PointScale scale){
        switch (scale){
            case week:
                return PointScale.day;
            case month:
                return PointScale.week;
            case year:
                return PointScale.month;
            default:
                return scale;
        }
    }

    public static PointScale nextScale(PointScale scale){
        switch (scale){
            case detail:
                return PointScale.day;
            case day:
                return PointScale.week;
            case week:
                return PointScale.month;
            case month:
                return PointScale.year;
            default:
                return scale;
        }
    }


}
