package entity.documents;

import entity.transport.TransportCustomer;
import entity.transport.Transportation;

import java.sql.Date;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
public class LoadPlan extends IDocument{
//    private int id;
//    private Date date;

    private Deal deal;
    private TransportCustomer customer;
    private Transportation transportation;
    private DocumentOrganisation documentOrganisation;


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
