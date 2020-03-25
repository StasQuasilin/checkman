package utils;

import com.itextpdf.text.DocumentException;
import constants.Constants;
import org.apache.log4j.Logger;
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

//    public static final String NUMBER_REGEX = "[A-ZА-ЯІ]{0,2}?\\s?\\d{4,5}\\s?[A-ZА-ЯІ]{2,3}";
    public static final String NUMBER_REGEX = "((\\W{2}?\\s?\\d{2}-?\\s?\\d{2})|(\\d{2}-?\\s?\\d-?\\s?\\d{2}))\\s?\\W{2}";
    private static final String SPACE = Constants.SPACE;
    private static final Logger log = Logger.getLogger(Parser.class);
    private static final String EMPTY = Constants.EMPTY;

    public synchronized List<String> parseVehicle(String value){
        value = value.toUpperCase().trim();
        Pattern pattern = Pattern.compile(NUMBER_REGEX);
        Matcher matcher = pattern.matcher(value);
        List<String> result = new ArrayList<>();

        while (matcher.find()){
            String group = matcher.group();
            log.info("Found '" + group + "'");
            value = value.replaceAll(group, EMPTY);
            result.add(prettyNumber(group.trim()));
        }
        String model = value.trim();
        if (U.exist(model)) {
            log.info("Model: " + model);
        }
        result.add(0, model);

        return result;
    }

    public String prettyNumber(String number){
        if (U.exist(number)) {
            StringBuilder builder = new StringBuilder();

            ArrayList<Character> characters = new ArrayList<>();
            for (char c : number.toCharArray()){
                if (Character.isLetter(c) || Character.isDigit(c)) {
                    characters.add(c);
                }
            }

            for (int i = 0 ; i < characters.size(); i++){
                char a = characters.get(i);
                builder.append(a);
                if (i < characters.size() - 1){
                    Character b = characters.get(i + 1);
                    if (Character.isLetter(a) != Character.isLetter(b)){
                        builder.append(SPACE);
                    }
                }
            }

            return builder.toString();
        } else {
            return number;
        }
    }

    public static void main(String[] args) throws IOException, DocumentException {
        Parser parser = new Parser();
        System.out.println(parser.parseVehicle("ДАФ ВМ 1869 ВІ ВІ 5800 ВІ"));
        System.out.println(parser.parseVehicle("ДАФ ВМ 18 69 ВІ"));
        System.out.println(parser.parseVehicle("Камаз 018-69 ВІ"));
        System.out.println(parser.parseVehicle(" ВМ 18-69 ВІ"));
        System.out.println(parser.parseVehicle(" 018 69 ВІ"));
    }


    public static List<String> parsePerson(String value) {
        value = value.replaceAll("\\.", EMPTY);

        String[] split = value.split(" ");
        List<String> result = new ArrayList<>();
        for (String s : split){
            if (s.length() > 1) {
                result.add(s.substring(0, 1).toUpperCase() + s.substring(1));
            } else if (U.exist(s)){
                result.add(s.toUpperCase() + ".");
            }
        }
        return result;
    }
}
