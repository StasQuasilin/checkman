package utils;

import java.util.ArrayList;
import java.util.List;
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

    static String prettyNumber(String number){
        number = number.replaceAll(" ", "");
        StringBuilder builder = new StringBuilder();

        Pattern pattern = Pattern.compile("^[A-ZА-Я]{0,3}");
        Matcher matcher = pattern.matcher(number);
        if (matcher.find()){
            String group = matcher.group();
            if (!group.isEmpty())
            builder.append(group).append(' ');
            number = number.replaceAll(group, "");
        }

        pattern = Pattern.compile("\\d*");
        matcher = pattern.matcher(number);
        if (matcher.find()){
            String group = matcher.group();
            int d = Math.round(1f * group.length() / 2);

            builder.append(group.substring(0, d)).append('-').append(group.substring(d));
            number = number.replaceAll(group, "");
        }

        pattern = Pattern.compile("^[A-ZА-Я]{2,3}");
        matcher = pattern.matcher(number);
        if (matcher.find()){
            String group = matcher.group();
            builder.append(' ').append(group);
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        for (String s : parseVehicle(" Man  AE45-54 AA 12556 ам")){
            System.out.println('\'' + s + '\'');
        }
        for (String s : parsePerson("Vasilina   Stanislav My.45")){
            System.out.println('\'' + s + '\'');
        }
    }

    public static List<String> parsePerson(String value) {
        value = value.replaceAll("\\s+", " ").replaceAll("[^a-z^A-Z^а-я^А-Я^\\s]", "").toLowerCase().trim();

        String[] split = value.split(" ");
        List<String> result = new ArrayList<>();
        for (String s : split){
            result.add(s.substring(0, 1).toUpperCase() + s.substring(1));
        }
        return result;
    }
}
