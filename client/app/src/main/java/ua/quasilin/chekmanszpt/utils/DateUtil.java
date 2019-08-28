package ua.quasilin.chekmanszpt.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by szpt_user045 on 28.08.2019.
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {
    private static final String TIME_FORMAT = "HH:mm";
    private static final String DATE_FORMAT = "dd MMMM yyyy";

    public static String getTime(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
        return dateFormat.format(date);
    }

    public static String getDate(Date date){
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }
}
