package utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
        final LocalDateTime now = LocalDateTime.now();
        final Timestamp t1 = Timestamp.valueOf(now);
        final Timestamp t2 = Timestamp.valueOf(now.plusHours(18).plusDays(1));
        long diff = t2.getTime() - t1.getTime();
        final double ceil = Math.ceil(1d * diff / (60 * 60 * 1000 * 24));
        System.out.println(ceil);
    }
}
