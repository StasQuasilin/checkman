package entity.transport;

import api.warehousing.WarehousingEditAPI;
import entity.Worker;
import entity.documents.Deal;
import entity.documents.LoadPlan;
import entity.documents.Shipper;
import entity.laboratory.SunAnalyses;
import entity.storages.StorageProduct;
import entity.weight.Weight;
import org.apache.log4j.Logger;
import utils.Archivator;
import utils.DocumentUIDGenerator;
import utils.UpdateUtil;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.io.IOException;
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
            if (transportation.getUsedStorages().size() == 0){
                List<StorageProduct> storageProducts = dao.getStorageProductByProduct(transportation.getProduct());
                if (storageProducts.size() > 0){
                    TransportStorageUsed used = new TransportStorageUsed();
                    used.setTransportation(transportation);
                    used.setStorage(storageProducts.get(0).getStorage());
                    used.setShipper(transportation.getShipper());
                    used.setAmount(1f * Math.round(transportation.getWeight().getNetto() * 100) / 100);
                    used.setCreate(new ActionTime(transportation.getCreator()));
                    dao.save(used.getCreate());
                    dao.save(used);
                }
            }
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


}
