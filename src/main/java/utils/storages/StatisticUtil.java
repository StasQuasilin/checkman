package utils.storages;

import entity.DealType;
import entity.StatisticEntry;
import entity.documents.Shipper;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.statistic.StatisticPeriodPoint;
import entity.storages.*;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by szpt_user045 on 08.10.2019.
 */
public class StatisticUtil extends IStorageStatisticUtil {

    private static final Logger log = Logger.getLogger(StatisticUtil.class);
    private final dbDAO dao = dbDAOService.getDAO();

    public StatisticUtil() {
    }

    public void removeStorageEntry(StorageDocument document){
        int documentId = document.getId();
        StatisticEntry entry = dao.getStatisticEntry(documentId);
        if (entry != null) {
            dao.remove(entry);
            dayStock(entry);
        }
    }

    public void updateStatisticEntry(Transportation document){
        int documentId = document.getId();
        StatisticEntry entry = dao.getStatisticEntry(documentId);

        boolean save = false;
        if (entry == null){
            entry = new StatisticEntry();
            entry.setDocument(documentId);
            save = true;
        }

        Date date = document.getDate();
        if (entry.getDate() == null || !entry.getDate().equals(date)){
            entry.setDate(date);
            save = true;
        }

        int prevOrganisationId = -1;
//        Organisation counterparty = document.getCounterparty();
        if (entry.getOrganisation() == null){
//            entry.setOrganisation(counterparty);
            save = true;
        }
//        else if (entry.getOrganisation().getId() != counterparty.getId()){
//            prevOrganisationId = entry.getOrganisation().getId();
//            entry.setOrganisation(counterparty);
//            save = true;
//        }

        int prevProductId = -1;
//        Product product = document.getProduct();
        if (entry.getProduct() == null){
//            entry.setProduct(product);
            save = true;
        }
//        else if (entry.getProduct().getId() != product.getId()){
//            prevProductId = entry.getProduct().getId();
//            entry.setProduct(product);
//            save = true;
//        }

        int prevShipperId = -1;
//        Shipper shipper = document.getShipper();
        if (entry.getShipper() == null){
//            entry.setShipper(shipper);
            save = true;
        }
//        else if(entry.getShipper().getId() != shipper.getId()){
//            prevShipperId = entry.getShipper().getId();
//            entry.setShipper(shipper);
//            save = true;
//        }

//        if (document.getWeight() != null) {
//            float amount = document.getWeight().getCorrectedNetto() * (document.getType() == DealType.buy ? 1 : -1);
//            if (entry.getAmount() != amount) {
//                entry.setAmount(amount);
//                save = true;
//            }
//        } else {
//            save = false;
//        }

        if (save){
            dao.save(entry);
            dayStock(entry);

            Organisation prevStorage;
            if (prevOrganisationId != -1){
                prevStorage = dao.getObjectById(Organisation.class, prevOrganisationId);
            } else {
                prevStorage = entry.getOrganisation();
            }

            Product prevProduct;
            if (prevProductId != -1){
                prevProduct = dao.getObjectById(Product.class, prevProductId);
            } else {
                prevProduct = entry.getProduct();
            }

            Shipper prevShipper;
            if (prevShipperId != -1){
                prevShipper = dao.getShipperById(prevShipperId);
            } else {
                prevShipper = entry.getShipper();
            }

            dayStock(entry.getDate(), prevStorage, prevProduct, prevShipper);
        }
    }

    public void dayStock(StatisticEntry entry){
        dayStock(entry.getDate(), entry.getOrganisation(), entry.getProduct(), entry.getShipper());
    }
    private void dayStock(Date date, Organisation organisation, Product product, Shipper shipper){
        LocalDate localDate = date.toLocalDate();

        float plusAmount = 0;
        float minusAmount = 0;

        for (StatisticEntry entry : dao.getStatisticEntries(date, Date.valueOf(localDate.plusDays(1)), organisation, product, shipper)){
            float amount = entry.getAmount();
            if (amount > 0){
                plusAmount += amount;
            } else {
                minusAmount += amount;
            }
        }
        if (plusAmount != 0) {
            updateStock(date, organisation, product, shipper, PointScale.day, plusAmount);
        }
        if (minusAmount != 0){
            updateStock(date, organisation, product, shipper, PointScale.day, minusAmount);
        }
        updateStockByStock(date, organisation, product, shipper, PointScale.week);
    }

    private void updateStockByStock(Date date, Organisation storage, Product product, Shipper shipper, PointScale scale) {
        LocalDate localDate = date.toLocalDate();
        Date beginDate = Date.valueOf(getBeginDate(localDate, scale));
        Date endDate = Date.valueOf(getEndDate(localDate, scale));
        float plusAmount = 0;
        float minusAmount = 0;
        for (StatisticPeriodPoint point : dao.getStoragePoints(StatisticPeriodPoint.class, beginDate, endDate, storage.getId(), product, shipper, prevScale(scale))){
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
            updateStockByStock(date, storage, product, shipper, nextScale);
        }
    }

    Timestamp now = Timestamp.valueOf(LocalDateTime.now());
    private void updateStock(Date date, Organisation organisation, Product product, Shipper shipper, PointScale scale, float amount){
        log.info(organisation.getName() + ": " + product.getName() + ": " + shipper.getValue() + " by " + scale.toString() + "=" + amount);
        StatisticPeriodPoint point = null;
        for (StatisticPeriodPoint spp : dao.getStoragePoints(StatisticPeriodPoint.class, date, date, organisation, product, shipper, scale)){
            if (spp.getAmount() != 0 && Math.signum(spp.getAmount()) == Math.signum(amount)){
                point = spp;
            } else if (spp.getAmount() == 0){
                dao.remove(spp);
            }
        }
        if (point == null) {
            point = new StatisticPeriodPoint();
            point.setDate(date);
            point.setScale(scale);
            point.setStorage(organisation);
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

    public synchronized void pureStocks(Date prev, Date to, Organisation storage, Product product, Shipper shipper, PointScale scale, ArrayList<StatisticPeriodPoint> points){
        getStocks(prev, to, storage, product, shipper, scale, points);
        ArrayList<StatisticPeriodPoint> temp = new ArrayList<>(points);
        for (StatisticPeriodPoint point : temp){
            PointScale pointScale = nextScale(point.getScale());
            if (pointScale != point.getScale()) {
                points.remove(point);
            }
        }
        temp.clear();
    }

    public synchronized void getStocks(Date prev, Date to, Object storage, Product product, Shipper shipper, PointScale scale, ArrayList<StatisticPeriodPoint> points){
        LocalDate localDate = getBeginDate(to.toLocalDate(), scale);
        Date date = Date.valueOf(localDate);

        points.addAll(dao.getStoragePoints(StatisticPeriodPoint.class, prev, date, storage, product, shipper, scale));

        if (points.size() > 0) {
            PointScale s = nextScale(scale);
            if (s != scale) {
                getStocks(date, to, storage, product, shipper, s, points);
            }
        }
    }
}
