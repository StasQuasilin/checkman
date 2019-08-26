package utils;

import entity.Role;
import entity.User;
import entity.Worker;
import entity.documents.LoadPlan;
import entity.laboratory.SunAnalyses;
import entity.transport.Transportation;
import entity.weight.Weight;
import org.apache.log4j.Logger;
import utils.boxes.IBox;
import utils.hibernate.Hibernator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public static void calculateWeight(Transportation transportation) {
        if (transportation.getWeight() != null) {
            SunAnalyses sunAnalyse = transportation.getSunAnalyses();
            if (sunAnalyse != null) {
                float humidity = (sunAnalyse.getHumidity1() + sunAnalyse.getHumidity2()) /
                        ((sunAnalyse.getHumidity1() > 0 ? 1 : 0) + (sunAnalyse.getHumidity2() > 0 ? 1 : 0));
                float soreness = sunAnalyse.getSoreness();

                float percentage = 0;
                if (humidity > HUMIDITY_BASIS && soreness > SORENESS_BASIS) {
                    percentage = 100 - ((100 - humidity) * (100 - soreness) * 100) / ((100 - HUMIDITY_BASIS) * (100 - SORENESS_BASIS));
                } else if (humidity > HUMIDITY_BASIS) {
                    percentage = ((humidity - HUMIDITY_BASIS) * 100) / (100 - HUMIDITY_BASIS);
                } else if (soreness > SORENESS_BASIS) {
                    percentage = ((soreness - SORENESS_BASIS) * 100 / (100 - SORENESS_BASIS));
                }

                Weight weight = transportation.getWeight();
                weight.setCorrection(percentage);
                dao.saveWeight(weight);
            }
        }
    }

    public static void archive(LoadPlan loadPlan) throws IOException {
        Transportation transportation = loadPlan.getTransportation();
        transportation.setArchive(true);
        dao.save(transportation);
        updateUtil.onArchive(loadPlan);
    }
}
