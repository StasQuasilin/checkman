package utils;

import com.itextpdf.text.DocumentException;
import utils.storages.PointScale;
import utils.storages.StorageUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by szpt_user045 on 21.03.2019.
 */
public class Parser {

    public static final String NUMBER_REGEX = "[A-ZА-Я]{0,3}\\s*\\d{2,3}\\W?\\d{2,3}\\s*[A-ZА-Я]{2,3}";

    public synchronized static List<String> parseVehicle(String value){
        value = value.toUpperCase().trim();
        StringBuilder builder = new StringBuilder();

        for (char c : value.toCharArray()){
           if (Character.isLetter(c) || Character.isDigit(c) || Character.isSpaceChar(c)){
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
        if (U.exist(number)) {
            number = number.toUpperCase().replaceAll(" ", "");

            StringBuilder builder = new StringBuilder();
            char[] chars = number.toCharArray();
            for (int i = 0 ; i < number.length(); i++){
                char a = chars[i];
                builder.append(a);
                if (i < number.length() - 1) {
                    char b = chars[i + 1];
                    if (Character.isLetter(a) && Character.isDigit(b) || Character.isDigit(a) && Character.isLetter(b)){
                        builder.append(' ');
                    }
                }
            }

            return builder.toString();
        } else {
            return number;
        }
    }

    public static void main(String[] args) throws IOException, DocumentException {
//        System.out.println(parseVehicle("daf вм 17-56 вм вм5241аа"));


    }


    public static List<String> parsePerson(String value) {
//        value = value.replaceAll("\\s+", " ").replaceAll("[^a-z^A-Zа-яА-ЯіІ\\s]", "").toLowerCase().trim();
        value = value.replaceAll("\\.", " ");

        String[] split = value.split(" ");
        List<String> result = new ArrayList<>();
        for (String s : split){
            if (s.length()> 1) {
                result.add(s.substring(0, 1).toUpperCase() + s.substring(1));
            } else {
                result.add(s.toUpperCase() + ".");
            }
        }
        return result;
    }
}
