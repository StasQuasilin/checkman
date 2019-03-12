package entity.documents;

import entity.IImportantObject;

import java.sql.Date;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
public abstract class IDocument extends IImportantObject{
    public int id;
    public Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
