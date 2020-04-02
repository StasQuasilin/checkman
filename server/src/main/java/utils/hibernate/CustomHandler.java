package utils.hibernate;

import constants.Constants;
import entity.documents.Deal;
import entity.organisations.Organisation;
import entity.transport.*;
import utils.storages.StatisticUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by szpt_user045 on 01.07.2019.
 */
public class CustomHandler implements Constants{

    static dbDAO dao = dbDAOService.getDAO();

    public static void main(String[] args) {
        Hibernator hibernator = Hibernator.getInstance();
        for (TransportationGroup group : hibernator.query(TransportationGroup.class, null)){
            Transportation transportation = group.getTransportation();
            group.setAddress(transportation.getAddress());
            hibernator.save(group);
        }

        HibernateSessionFactory.shutdown();
    }

    static String pretty(String number){
        StringBuilder builder = new StringBuilder();
        for (char c : number.toCharArray()){
            if (Character.isDigit(c) || Character.isLetter(c)){
                builder.append(c);
            }
        }
        return builder.toString();
    }
}
