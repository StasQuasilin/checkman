package utils;

import entity.reports.ReportField;
import entity.reports.ReportFieldCategory;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public class U {
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

    public synchronized static String getNumberByWords(float v) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1000000; i >= 1; i/=10){
            double v2 = Math.floor(v / i);
            if (v2 >= 1) {
                System.out.println(v2 + ":" + v2 * i);
                System.out.println(base.get(String.valueOf(v2)));
                return null;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        getNumberByWords(1285200.58f);
    }
}
