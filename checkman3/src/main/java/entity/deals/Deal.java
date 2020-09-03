package entity.deals;

import entity.ActionTime;
import entity.references.Organisation;

import java.sql.Date;

public class Deal {
    private int id;
    private String number;
    private Date date;
    private Date from;
    private Date to;
    private Organisation counterparty;
    private ActionTime created;
}
