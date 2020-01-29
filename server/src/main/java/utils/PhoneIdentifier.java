package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by szpt_user045 on 29.01.2020.
 */
public class PhoneIdentifier {
    public static final String NUMBER_PATTERN = "\\+?(38)?0((50)|(63)|(66)|(67)|(68)|(73)|(91)|(92)|(93)|(94)|(95)|(96)|(97)|(98)|(99))\\d{7}";
    public boolean identify(String target){
        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public static void main(String[] args) {
        PhoneIdentifier identifier = new PhoneIdentifier();
        identifier.identify("+380509657989");
    }
}
