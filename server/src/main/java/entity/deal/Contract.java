package entity.deal;

import entity.Worker;
import entity.documents.Shipper;
import entity.organisations.Counterparty;
import entity.transport.ActionTime;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by szpt_user045 on 08.11.2019.
 */
@Entity
@Table(name = "_contracts")
public class Contract {
    private int id;
    private Date from;
    private Date to;
    private Counterparty counterparty;
    private Shipper shipper;
    private String number;
    private Set<ContractProduct> products = new HashSet<>();
    private Set<ContractNote> notes = new HashSet<>();
    private Worker manager;
    private ActionTime createTime;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_from")
    public Date getFrom() {
        return from;
    }
    public void setFrom(Date from) {
        this.from = from;
    }

    @Basic
    @Column(name = "_to")
    public Date getTo() {
        return to;
    }
    public void setTo(Date to) {
        this.to = to;
    }

    @OneToOne
    @JoinColumn(name = "counterparty")
    public Counterparty getCounterparty() {
        return counterparty;
    }
    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }

    @OneToOne
    @JoinColumn(name = "shipper")
    public Shipper getShipper() {
        return shipper;
    }
    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    @Basic
    @Column(name = "number")
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "contract", cascade = CascadeType.ALL)
    public Set<ContractProduct> getProducts() {
        return products;
    }
    public void setProducts(Set<ContractProduct> products) {
        this.products = products;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "contract", cascade = CascadeType.ALL)
    public Set<ContractNote> getNotes() {
        return notes;
    }
    public void setNotes(Set<ContractNote> notes) {
        this.notes = notes;
    }

    @OneToOne
    @JoinColumn(name = "manager")
    public Worker getManager() {
        return manager;
    }
    public void setManager(Worker manager) {
        this.manager = manager;
    }

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }
}
