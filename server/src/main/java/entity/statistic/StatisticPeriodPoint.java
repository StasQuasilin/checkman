package entity.statistic;

import entity.JsonAble;
import entity.documents.Shipper;
import entity.organisations.Organisation;
import entity.products.Product;
import org.json.simple.JSONObject;
import utils.storages.PointScale;
import utils.storages.StorageUtil;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by szpt_user045 on 09.10.2019.
 */
@Entity
@Table(name="statistic_period_points")
public class StatisticPeriodPoint extends JsonAble{
    private int id;
    private Date date;
    private PointScale scale;
    private Organisation storage;
    private Product product;
    private Shipper shipper;
    private float amount;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "scale")
    public PointScale getScale() {
        return scale;
    }
    public void setScale(PointScale scale) {
        this.scale = scale;
    }

    @OneToOne
    @JoinColumn(name = "counterparty")
    public Organisation getStorage() {
        return storage;
    }
    public void setStorage(Organisation storage) {
        this.storage = storage;
    }

    @OneToOne
    @JoinColumn(name = "product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
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
    @Column(name = "amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "StoragePeriodPoint {\n" +
                "\tdate=" + date + "\n" +
                "\tscale=" + scale + "\n" +
                "\tstorage=" + storage + "\n" +
                "\tproduct=" + product + "\n" +
                "\tshipper=" + shipper + "\n" +
                "\tamount=" + amount + "\n" +
                '}';
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(DATE, date.toString());
        object.put(COUNTERPARTY, storage.toJson());
        object.put(PRODUCT, product.toJson());
        object.put(SHIPPER, shipper.toJson());
        object.put(AMOUNT, amount);
        object.put(SCALE, scale.toString());
        PointScale s = scale;
        LocalDate localDate = date.toLocalDate();
        while ((s = StorageUtil.nextScale(s)) != scale){
            scale = s;
            LocalDate beginDate = StorageUtil.getBeginDate(localDate, scale);
            object.put(scale.toString(), Date.valueOf(beginDate).toString());
        }
        return object;
    }
}
