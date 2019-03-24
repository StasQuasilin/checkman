package utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public class DateUtil {
    public static Date parseFromEditor(String dateFrom) {
        return parseFromEditor(dateFrom, "d.M.y");
    }

    public static Date parseFromEditor(String date, String format){
        try {
            return new Date(parseToLong(date, format));
        } catch (ParseException e) {
            return Date.valueOf(LocalDate.now());
        }
    }

    public static long parseToLong(String date, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(date).getTime();
    }

    public static void main(String[] args) {
        int i = 125;
        long l = 125;
        System.out.println(Integer.hashCode(i) + ": " + Long.hashCode(l));
    }

}
