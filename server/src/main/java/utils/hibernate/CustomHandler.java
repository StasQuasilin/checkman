package utils.hibernate;

import constants.Constants;
import entity.documents.Deal;
import entity.organisations.Organisation;
import entity.transport.ActionTime;
import entity.transport.TransportCustomer;
import entity.transport.Transportation;
import entity.transport.Vehicle;
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
        HashMap<String, Object> params = hibernator.getParams();
        params.put("create", null);
        ActionTime at;
        LocalTime time = LocalTime.of(0, 0);
        for (Deal deal : hibernator.query(Deal.class, params)){
            at = new ActionTime();
            at.setCreator(deal.getCreator());
            at.setTime(Timestamp.valueOf(LocalDateTime.of(deal.getDate().toLocalDate(), time)));
            hibernator.save(at);
            deal.setCreate(at);
            hibernator.save(deal);
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
