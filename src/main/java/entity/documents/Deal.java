package entity.documents;

import constants.Constants;
import entity.DealType;
import entity.JsonAble;
import entity.deal.DeliveryCost;
import entity.organisations.Address;
import entity.products.Product;
import entity.Worker;
import entity.organisations.Organisation;
import entity.transport.ActionTime;
import entity.weight.Unit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.U;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "deals")
public class Deal extends JsonAble{
    private int id;
    private Date date;
    private Date dateTo;
    private String number;
    private DealType type;
    private Organisation organisation;
    private Address address;
    private Set<DealProduct> products;
    private Set<DeliveryCost> costs;

    private String uid;
    @Deprecated
    private Product product;
    @Deprecated
    private Unit unit;
    @Deprecated
    private Shipper shipper;
    @Deprecated
    private float price;

    private float complete;
    private float quantity;
    private boolean done;
    private boolean archive;
    private ActionTime create;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = DEAL, cascade = CascadeType.ALL)
    public Set<DealProduct> getProducts() {
        return products;
    }
    public void setProducts(Set<DealProduct> products) {
        this.products = products;
    }

    @OneToOne
    @JoinColumn(name = "created")
    public ActionTime getCreate() {
        return create;
    }
    public void setCreate(ActionTime create) {
        this.create = create;
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
    @Column(name = "_number")
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
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
    @Deprecated
    public Shipper getShipper() {
        return shipper;
    }
    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    @OneToOne
    @JoinColumn(name = "organisation")
    public Organisation getOrganisation() {
        return organisation;
    }
    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    @OneToOne
    @JoinColumn(name = "address")
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    @OneToOne
    @JoinColumn(name = "product")
    @Deprecated
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @Basic
    @Column(name = "quantity")
    @Deprecated
    public float getQuantity() {
        return quantity;
    }
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @OneToOne
    @JoinColumn(name = "unit")
    @Deprecated
    public Unit getUnit() {
        return unit;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "price")
    @Deprecated
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

    @Transient
    @Deprecated
    public Worker getCreator() {
        return create.getCreator();
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "deal", cascade = CascadeType.ALL)
    public Set<DeliveryCost> getCosts() {
        return costs;
    }
    public void setCosts(Set<DeliveryCost> coasts) {
        this.costs = coasts;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return getClass() == obj.getClass() && hashCode() == obj.hashCode();
    }

    @Override
    public String toString() {
        return "Deal{\n" +
                "\tdateTo=" + dateTo + ",\n" +
                "\ttype=" + type + ",\n" +
                "\torganisation=" + organisation + ",\n" +
                "\tuid='" + uid + '\'' + ",\n" +
                '}';
    }

    @Override
    public JSONObject toShortJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(TYPE, type.toString());
        if (organisation != null) {
            object.put(COUNTERPARTY, organisation.toShortJson());
        }
//        object.put(SHIPPER, shipper.toJson());
//        object.put(PRODUCT, product.toJson());

//        object.put(QUANTITY, quantity);
//        object.put(UNIT, unit.toJson());
//        object.put(PRICE, price);
        if (U.exist(number)){
            object.put(NUMBER, number);
        }

        return object;
    }

    private int target() {
        if (products != null && products.size() > 0){
            int target = 0;
            for(DealProduct product : products){
                target += product.getQuantity();
            }
            return target;
        }
        return (int) quantity;
    }

    private int done() {
        if (products != null && products.size() > 0){
            int done = 0;
            for(DealProduct product : products){
                done += product.getDone();
            }
            return done;
        }
        return (int) complete;
    }

    private JSONArray products() {
        JSONArray array = new JSONArray();
        if (products != null && products.size() > 0) {
            for (DealProduct product : products) {
                array.add(product.toJson());
            }
        } else {
            for(int i = 0; i < 1; i++) {
                final JSONObject object = new JSONObject();
                object.put(ID, id);
                object.put(PRODUCT_ID, product.getId());
                object.put(PRODUCT_NAME, product.getName());
                object.put(QUANTITY, quantity);
                object.put(UNIT_ID, unit.getId());
                object.put(UNIT_NAME, unit.getName());
                object.put(SHIPPER_ID, shipper.getId());
                object.put(SHIPPER_NAME, shipper.getValue());
                object.put(PRICE, price);
                array.add(object);
            }
        }
        return array;
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject json = toShortJson();
        json.put(PRODUCTS, products());
        json.put(DATE, date.toString());
        if (dateTo != null) {
            json.put(TO, dateTo.toString());
        }
        if (organisation != null) {
            json.put(ORGANISATION, organisation.toJson());
        }
        json.put(COMPLETE, done());
        json.put(TARGET, target());
        json.put(DONE, isDone());
        json.put(ARCHIVE, isArchive());
        json.put(CREATE, create.toShortJson());
        json.put(Constants.COSTS, costs());
        return json;
    }

    private JSONArray costs() {
        JSONArray array = new JSONArray();
        if(costs != null) {
            for (DeliveryCost cost : costs) {
                array.add(cost.toJson());
            }
        }
        return array;
    }
}
