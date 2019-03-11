package entity.documents;

import entity.organisations.Organisation;

import java.sql.Date;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
public class Deal extends IDocument{
//    private int id;
//    private Date date;

    private Date dateTo;
    private Organisation organisation;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }
}
