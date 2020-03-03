package utils.storages;

import java.time.LocalDate;

/**
 * Created by szpt_user045 on 26.02.2020.
 */
public abstract class IStorageStatisticUtil {

    public static synchronized LocalDate getBeginDate(LocalDate date, PointScale scale){
        int minus;
        switch (scale){
            case week:
                minus = Math.min(toBegin(date, scale), toBegin(date, PointScale.month));
                break;
            default:
                minus = toBegin(date, scale);
        }

        return date.minusDays(minus);
    }

    public static synchronized LocalDate getEndDate(LocalDate date, PointScale scale){
        int plus;
        switch (scale){
            case week:
                plus = Math.min(toEnd(date, scale), toEnd(date, PointScale.month));
                break;
            default:
                plus = toEnd(date, scale);
        }
        return date.plusDays(plus);
    }

    public static int toBegin(LocalDate date, PointScale scale){
        switch (scale){
            case day:
                return 0;
            case week:
                return date.getDayOfWeek().getValue() - 1;
            case month:
                return date.getDayOfMonth() - 1;
            case year:
                return date.getDayOfYear() - 1;
            default:
                return 0;
        }
    }

    public static int toEnd(LocalDate date, PointScale scale){
        switch (scale){
            case day:
                return 0;
            case week:
                return 7 - date.getDayOfWeek().getValue();
            case month:
                return date.lengthOfMonth() - date.getDayOfMonth();
            case year:
                return date.lengthOfYear() - date.getDayOfYear();
            default:
                return 0;
        }
    }

    public PointScale prevScale(PointScale scale){
        switch (scale){
            case year:
                return PointScale.month;
            case month:
                return PointScale.week;
            case week:
                return PointScale.day;
            default:
                return scale;
        }
    }

    public static PointScale nextScale(PointScale scale){
        switch (scale){
            case detail:
                return PointScale.day;
            case day:
                return PointScale.week;
            case week:
                return PointScale.month;
            case month:
                return PointScale.year;
            default:
                return scale;
        }
    }
}
