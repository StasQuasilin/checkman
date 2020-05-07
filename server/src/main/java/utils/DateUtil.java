package utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public static String prettyDate(Date date, String format){
        return new SimpleDateFormat(format).format(date);
    }

    public static String prettyDate(Date date){
        return prettyDate(date, "dd.MM.yy");
    }

    public static void main(String[] args) {
        final Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        System.out.println(prettyDate(timestamp));
    }

    public static String prettyDate(Timestamp time) {
        LocalDateTime dateTime = time.toLocalDateTime();
        return prettyDate(Date.valueOf(dateTime.toLocalDate())) + ", " + dateTime.toLocalTime().toString().substring(0, 5);
    }
}
