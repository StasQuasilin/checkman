package utils;

import entity.Role;
import entity.User;
import entity.Worker;
import entity.transport.Transportation;
import utils.hibernate.Hibernator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by quasilin on 18.03.2019.
 */
public class TransportUtil {

    public static final Hibernator hibernator = Hibernator.getInstance();

    public static List<Worker> getLaboratoryPersonal(){
        return hibernator.query(User.class, "role", Role.analyser).stream().map(User::getWorker).collect(Collectors.toList());
    }
}
