package utils.hibernate.DateContainers;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by szpt_user045 on 22.11.2018.
 */
public class BETWEEN {
    private Timestamp from;
    private Timestamp to;

    public BETWEEN(Timestamp from, Timestamp to) {
        this.from = from;
        this.to = to;
    }

    public BETWEEN(LocalDateTime from, LocalDateTime to) {
        this.from = Timestamp.valueOf(from);
        this.to = Timestamp.valueOf(to);
    }

    public BETWEEN(Date from, Date to) {
        this.from = Timestamp.valueOf(LocalDateTime.of(from.toLocalDate(), LocalTime.of(0, 0)));
        this.to = Timestamp.valueOf(LocalDateTime.of(to.toLocalDate(), LocalTime.of(23, 59)));
    }

    public Timestamp getFrom() {
        return from;
    }

    public void setFrom(Timestamp from) {
        this.from = from;
    }

    public Timestamp getTo() {
        return to;
    }

    public void setTo(Timestamp to) {
        this.to = to;
    }
}
