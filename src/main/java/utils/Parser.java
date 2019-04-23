package utils;

import api.sign.SignUpAPI;
import bot.BotFactory;
import constants.Branches;
import entity.transport.Transportation;
import utils.email.RegistratorEmail;
import utils.hibernate.HibernateSessionFactory;
import utils.hibernate.Hibernator;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by szpt_user045 on 21.03.2019.
 */
public class Parser {

    static final String NUMBER_REGEX = "[A-ZА-Я]{0,3}\\s*\\d{2,3}\\W?\\d{2,3}\\s*[A-ZА-Я]{2,3}";

    public synchronized static List<String> parseVehicle(String value){
        value = value.toUpperCase().trim();
        StringBuilder builder = new StringBuilder();

        for (char c : value.toCharArray()){
           if (Character.isLetter(c) || Character.isDigit(c) || c == ' '){
               builder.append(c);
           }
        }

        value = builder.toString();
        Pattern pattern = Pattern.compile(NUMBER_REGEX);
        Matcher matcher = pattern.matcher(value);
        List<String> result = new ArrayList<>();

        while (matcher.find()){
            String group = matcher.group();
            value = value.replaceAll(group, "");
            result.add(prettyNumber(group.trim()));
        }
        result.add(0, value.trim());
        return result;
    }

    public static String prettyNumber(String number){
        number = number.toUpperCase().replaceAll(" ", "");
        StringBuilder builder = new StringBuilder();

        Pattern pattern = Pattern.compile("^[A-ZА-Я]{0,3}");
        Matcher matcher = pattern.matcher(number);
        if (matcher.find()){
            String group = matcher.group();
            if (!group.isEmpty())
            builder.append(group).append(' ');
            number = number.replaceAll(group, "");
        }

        pattern = Pattern.compile("\\d*\\-?\\d*");
        matcher = pattern.matcher(number);
        if (matcher.find()){
            String group = matcher.group();
            number = number.replaceAll(group, "");
            group = group.replaceAll("\\D", "");
            int d = Math.round(1f * group.length() / 2);

            builder.append(group.substring(0, d)).append('-').append(group.substring(d));

        }

        pattern = Pattern.compile("^[A-ZА-Я]{0,3}");
        matcher = pattern.matcher(number);
        if (matcher.find()){
            String group = matcher.group();
            builder.append(' ').append(group);
        }

        return builder.toString();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(prettyNumber("АМ 15-65 АА"));
    }


    public static List<String> parsePerson(String value) {
        value = value.replaceAll("\\s+", " ").replaceAll("[^a-z^A-Zа-яА-ЯіІ\\s]", "").toLowerCase().trim();

        String[] split = value.split(" ");
        List<String> result = new ArrayList<>();
        for (String s : split){
            result.add(s.substring(0, 1).toUpperCase() + s.substring(1));
        }
        return result;
    }
}
