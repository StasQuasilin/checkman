package entity.transport;

import entity.Worker;
import entity.documents.DocumentOrganisation;
import entity.documents.IDocument;

import java.sql.Date;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
public class Transportation extends IDocument{
//    private int id;
//    private Date date;

    private Vehicle vehicle;
    private Driver driver;
    private DocumentOrganisation documentOrganisation;
    private ActionTime timeIn;
    private ActionTime timeOut;
    private Worker creator;
    private boolean archive;

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
