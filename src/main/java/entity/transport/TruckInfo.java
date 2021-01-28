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
    private String model;
    private String year;
    private String color;
    private String vin;
    private String brand;
    private String kind;
    private String category;
    private String document;
    private String number;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    @Column(name = "color")
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
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
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", category='" + category + '\'' +
                ", document='" + document + '\'' +
                ", year='" + year + '\'' +
                ", kind='" + kind + '\'' +
                ", color='" + color + '\'' +
                ", vin='" + vin + '\'' +
                '}';
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(BRAND, brand);
        json.put(MODEL, model);
        json.put(CATEGORY, category);
        json.put(DOCUMENT, document);
        json.put(YEAR, year);
        json.put(KIND, kind);
        json.put(COLOR, color);
        json.put(VIN, vin);
        return json;
    }
}
