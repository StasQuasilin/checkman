package entity.transport;

import entity.JsonAble;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by szpt_user045 on 19.12.2019.
 */
@Entity
@Table(name = "truck_info")
public class TruckInfo extends JsonAble{
    private int id;
    private Vehicle truck;
    private String number;
    private String brand;
    private String model;
    private String category;
    private String document;
    private String year;
    private String fuel;
    private String kind;
    private Date date;
    private String color;
    private int engineVolume;
    private int weight;
    private String action;
    private String type;
    private String body;
    private String vin;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "truck")
    public Vehicle getTruck() {
        return truck;
    }
    public void setTruck(Vehicle truck) {
        this.truck = truck;
    }

    @Basic
    @Column(name = "number")
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "brand")
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Basic
    @Column(name = "model")
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "fuel")
    public String getFuel() {
        return fuel;
    }
    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    @Basic
    @Column(name = "kind")
    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }

    @Basic
    @Column(name = "_year")
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }

    @Basic
    @Column(name = "category")
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "document")
    public String getDocument() {
        return document;
    }
    public void setDocument(String number) {
        this.document = number;
    }

    @Basic
    @Column(name = "_date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "color")
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    @Basic
    @Column(name = "engine_volume")
    public int getEngineVolume() {
        return engineVolume;
    }
    public void setEngineVolume(int engineVolume) {
        this.engineVolume = engineVolume;
    }

    @Basic
    @Column(name = "weight")
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "_action")
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "body")
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    @Basic
    @Column(name = "vin")
    public String getVin() {
        return vin;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }

    @Override
    public String toString() {
        return "TruckInfo{" +
                "id=" + id +
                ", truck=" + truck +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", category='" + category + '\'' +
                ", document='" + document + '\'' +
                ", year='" + year + '\'' +
                ", fuel='" + fuel + '\'' +
                ", kind='" + kind + '\'' +
                ", date=" + date +
                ", color='" + color + '\'' +
                ", engineVolume=" + engineVolume +
                ", weight=" + weight +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                ", body='" + body + '\'' +
                ", vin='" + vin + '\'' +
                '}';
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(BRAND, brand);
        json.put(VIN, vin);
        return json;
    }
}
