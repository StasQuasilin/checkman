package utils.hibernate;

import constants.Constants;
import entity.documents.Deal;
import entity.organisations.Organisation;
import entity.transport.*;
import entity.weight.RoundReport;
import utils.storages.StatisticUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by szpt_user045 on 01.07.2019.
 */
public class CustomHandler implements Constants{

    static dbDAO dao = dbDAOService.getDAO();

    public static void main(String[] args) {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        System.out.println(timestamp.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        System.out.println(timestamp.toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        System.out.println(timestamp.toLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH.mm")));
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
