package utils;

import entity.Role;
import entity.User;
import entity.Worker;
import entity.transport.Transportation;
import entity.weight.Weight;
import utils.boxes.IBox;
import utils.hibernate.Hibernator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by quasilin on 18.03.2019.
 */
public class TransportUtil extends IBox{

    public static final Hibernator hibernator = Hibernator.getInstance();

    public static List<Worker> getLaboratoryPersonal(){
        return hibernator.query(User.class, "role", Role.analyser).stream().map(User::getWorker).collect(Collectors.toList());
    }

    public static void checkTransport(Transportation transportation) {
        boolean isArchive = true;
        for (Weight weight : transportation.getWeights()){
            if (weight.getNetto() == 0){
                isArchive = false;
            }
        }
        if (transportation.getTimeIn() == null) {
            isArchive = false;
        }
        if (transportation.getTimeOut() == null) {
            isArchive = false;
        }
        if (transportation.isArchive() != isArchive) {
            transportation.setArchive(isArchive);
            HIBERNATOR.save(transportation);
        }
    }
}
