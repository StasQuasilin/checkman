package entity.transportations;

import entity.ActionTime;
import entity.deals.Shipper;
import entity.references.Driver;
import entity.references.Trailer;
import entity.references.Vehicle;
import entity.weight.Weight;

import java.sql.Date;

public class Transportation {
    private int id;
    private Date date;
    private Driver driver;
    private Vehicle vehicle;
    private Trailer trailer;
    private Shipper shipper;
    private Weight weight;
    private ActionTime registration;
    private ActionTime timeIn;
    private ActionTime timeOut;
}
