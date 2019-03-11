package utils.hibernate.DateContainers;

import java.sql.Date;

/**
 * Created by szpt_user045 on 22.11.2018.
 */
public class BETWEEN {
    private Date from;
    private Date to;

    public BETWEEN(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
