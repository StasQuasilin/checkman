package entity.deal;

import entity.Address;
import entity.DealType;
import entity.JsonAble;
import entity.Worker;
import entity.organisations.Organisation;
import entity.transport.ActionTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 08.11.2019.
 */
@Entity
@Table(name = "_contracts")
public class Contract extends JsonAble {
    private int id;
    private Date from;
    private Date to;
    private Organisation counterparty;
    private Address address;
    private String number;
    private Set<ContractProduct> products = new HashSet<>();
    private Set<ContractNote> notes = new HashSet<>();
    private boolean archive;
    private Worker manager;
    private ActionTime createTime;
    private String uid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public Organisation getCounterparty() {
        return counterparty;
    }
    public void setCounterparty(Organisation counterparty) {
        this.counterparty = counterparty;
    }

    @OneToOne
    @JoinColumn(name = "address")
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
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

    @Basic
    @Column(name = "archive")
    public boolean isArchive() {
        return archive;
    }
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Basic
    @Column(name = "uid")
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(DATE, from.toString());
        json.put(TO, to.toString());
        json.put(COUNTERPARTY, counterparty.toJson());
        if (address != null){
            json.put(ADDRESS, address.toJson());
        }
        json.put(NUMBER, number);
        JSONArray array = pool.getArray();
        array.addAll(products.stream().map(ContractProduct::toJson).collect(Collectors.toList()));
        json.put(PRODUCTS, array);
        json.put(ARCHIVE, archive);
        return json;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return getClass().equals(obj.getClass()) && hashCode() == obj.hashCode();
    }
}
