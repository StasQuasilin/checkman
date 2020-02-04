package utils;

import constants.Constants;
import entity.Gender;
import entity.reports.ReportField;
import entity.reports.ReportFieldCategory;

import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public class U implements Constants {
    private static final LanguageBase base = LanguageBase.getBase();
    public static String getParsableString(String s){
        return s.replaceAll("\"", "\"");
    }

    public static synchronized boolean exist(String value){
        return value != null && !value.isEmpty() && !value.equals("0");
    }

    public static boolean exist (String ... values){
        for (String value : values){
            if (!exist(value)){
                return false;
            }
        }
        return true;
    }

    static final Comparator<ReportFieldCategory> customComparator = (o1, o2) -> {
        if (o1 != null && o2 != null){
            return o2.getNumber() - o1.getNumber();
        } else if (o1 == null){
            return -1;
        } else {
            return 1;
        }
    };
    public static void sortCategories(List<ReportFieldCategory> categories){
        categories.sort(customComparator);
    }

    static final Comparator<ReportField> fieldComparator = (o1, o2) -> {
        if (o1.getCategory() != null && o2.getCategory() != null){
            return o1.getCategory().getNumber() - o2.getCategory().getNumber();
        } else if (o2.getCategory() == null){
            return 1;
        } else {
            return -1;
        }
    };
    public static void sort(List<ReportField> fields) {
        fields.sort(fieldComparator);
    }

    public static boolean equals(Object o1, Object o2) {
        if  (o1 == null && o2 == null){
            return true;
        } else if (o1 != null && o2 != null){
            return o1.hashCode() == o2.hashCode() ;
        }
        return false;
    }

    public synchronized static String getNumberByWords(double v, Gender gender, String unit1, String unit2){
        float tail = 1f * Math.round((v % 1) * 1000) / 1000;
        long round = Math.round(v - tail);
        return getNumberByWords(round, gender) + unit1 + SPACE + (int)(tail * 1000) + SPACE + unit2;
    }

    public synchronized static String getNumberByWords(double v, Gender gender) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1000000; i >= 1; i/=1000){
            int v1 = (int) Math.floor(v / i);
            int v2 = v1 * i;

            if (v1 > 0) {
                divide(builder, v1, i, gender, 100);
            }
            if (v2 >= 1000 ){
                builder.append(base.get(buildKey(i, v1))).append(SPACE);
            }
            v -= v2;
        }
        return builder.toString();
    }

    private static void divide(StringBuilder builder, int number, int step, Gender gender, int divider){
        if (number > 19) {
            int v1 = (int) Math.floor(number / divider);
            int v2 = v1 * divider;
            if (v2 > 0) {
                getString(builder, v2, step, gender);
            }
            divide(builder, number - v2, step,gender, divider / 10);
        } else {
            getString(builder, number, step, gender);
        }
    }

    private static void getString(StringBuilder builder, int v, int step, Gender gender){
        if (v > 0) {
            String s = String.valueOf(v);
            if (v == 1 || v == 2) {
                if (step == 1000 || (step == 1 && gender == Gender.female)) {
                    s += ".m";
                }
            }
            builder.append(base.get(s)).append(SPACE);
        }
    }

    private static String buildKey(int value, int count){
        int l = count > 19 ? count % 100 : count;
        if (l == 1){
            return String.valueOf(value + DOT + 1);
        } else if (l > 1 && l < 5) {
            return String.valueOf(value + DOT + 2);
        } else {
            return String.valueOf(value + DOT + 5);
        }
    }

    public static void main(String[] args) {
        Date date = Date.valueOf(LocalDate.now());
        Locale locale = new Locale("UK", "uk");
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, locale);
        String formattedDate = df.format(date);
        System.out.println(formattedDate);
    }

    public static String getNumberByWords(double v, String unit1, String unit2) {
        float tail = 1f * Math.round((v % 1) * 1000) / 1000;
        long round = Math.round(v - tail);
        return round + SPACE + unit1 + SPACE + (int)(tail * 100) + SPACE + unit2;
    }
}
