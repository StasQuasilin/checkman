package utils;

import entity.Role;
import entity.User;
import entity.Worker;
import entity.laboratory.SunAnalyses;
import entity.laboratory.transportation.SunTransportationAnalyses;
import entity.transport.Transportation;
import entity.weight.Weight;
import org.apache.log4j.Logger;
import utils.boxes.IBox;
import utils.hibernate.Hibernator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by quasilin on 18.03.2019.
 */
public class TransportUtil extends IBox{

    private final Logger log = Logger.getLogger(TransportUtil.class);

    public static List<Worker> getLaboratoryPersonal(){
        return hibernator.query(User.class, "role", Role.analyser).stream().map(User::getWorker).collect(Collectors.toList());
    }

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
        if (transportation.isArchive() != isArchive) {
            transportation.setArchive(isArchive);
            hibernator.save(transportation);
        }
    }
    public static final int HUMIDITY_BASIS = 7;
    public static final int SORENESS_BASIS = 3;
    public static void calculateWeight(Transportation transportation) {
        SunAnalyses sunAnalyse = transportation.getSunAnalyse();
        if ( sunAnalyse != null){
            float humidity = (sunAnalyse.getHumidity1() + sunAnalyse.getHumidity2()) /
                    ((sunAnalyse.getHumidity1() > 0 ? 1 : 0) + (sunAnalyse.getHumidity2() > 0 ? 1 : 0));
            float soreness = sunAnalyse.getSoreness();

            float percentage = 0;
            if (humidity > HUMIDITY_BASIS && soreness > SORENESS_BASIS){
                percentage = 100 - ((100-humidity)*(100-soreness)*100)/((100-HUMIDITY_BASIS)*(100-SORENESS_BASIS));
            } else if (humidity > HUMIDITY_BASIS){
                percentage = ((humidity - HUMIDITY_BASIS) * 100) / (100 - HUMIDITY_BASIS);
            } else if (soreness > SORENESS_BASIS){
                percentage = ((soreness - SORENESS_BASIS) * 100 / (100 - SORENESS_BASIS));
            }

            Weight weight = transportation.getWeight();
            weight.setCorrection(percentage);
            hibernator.save(weight);
        }
    }
}
