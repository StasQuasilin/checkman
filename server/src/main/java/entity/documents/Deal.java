package entity.documents;

import entity.DealType;
import entity.organisations.Counterparty;
import entity.products.Product;
import entity.Worker;
import entity.weight.WeightUnit;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "deals")
public class Deal{
    private int id;
    private Date date;
    private Date dateTo;
    private DealType type;
    private Shipper shipper;
    private Counterparty organisation;
    private Product product;
    private float quantity;
    private WeightUnit unit;
    private float price;
    private float complete;
    private Worker creator;
    private String uid;
    private boolean done;
    private boolean archive;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "date_to")
    public Date getDateTo() {
        return dateTo;
    }
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    public DealType getType() {
        return type;
    }
    public void setType(DealType type) {
        this.type = type;
    }

    @OneToOne
    @JoinColumn(name = "visibility")
    public Shipper getShipper() {
        return shipper;
    }
    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    @OneToOne
    @JoinColumn(name = "organisation")
    public Counterparty getOrganisation() {
        return organisation;
    }
    public void setOrganisation(Counterparty organisation) {
        this.organisation = organisation;
    }

    @OneToOne
    @JoinColumn(name = "product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @Basic
    @Column(name = "quantity")
    public float getQuantity() {
        return quantity;
    }
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @OneToOne
    @JoinColumn(name = "unit")
    public WeightUnit getUnit() {
        return unit;
    }
    public void setUnit(WeightUnit unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "price")
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    @Basic
    @Column(name = "complete")
    public float getComplete() {
        return complete;
    }
    public void setComplete(float done) {
        this.complete = done;
    }

    @OneToOne
    @JoinColumn(name = "creator")
    public Worker getCreator() {
        return creator;
    }
    public void setCreator(Worker creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "uid")
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "done")
    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }

    @Basic
    @Column(name = "archive")
    public boolean isArchive() {
        return archive;
    }
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Deal{\n" +
                "\tdateTo=" + dateTo + ",\n" +
                "\ttype=" + type + ",\n" +
                "\torganisation=" + organisation + ",\n" +
                "\tcreator=" + creator + ",\n" +
                "\tuid='" + uid + '\'' + ",\n" +
                '}';
    }
}
