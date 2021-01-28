package entity.organisations;

import entity.JsonAble;
import org.json.simple.JSONObject;
import utils.LanguageBase;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 29.11.2019.
 */
@Entity
@Table(name = "address")
public class Address extends JsonAble {
    private int id;
    private String index;
    private String region;
    private String district;
    private String city;
    private String street;
    private String build;
    private String block;
    private String flat;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "index")
    public String getIndex() {
        return index;
    }
    public void setIndex(String index) {
        this.index = index;
    }

    @Basic
    @Column(name = "region")
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }

    @Basic
    @Column(name = "district")
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "street")
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "build")
    public String getBuild() {
        return build;
    }
    public void setBuild(String build) {
        this.build = build;
    }

    @Basic
    @Column(name = "block")
    public String getBlock() {
        return block;
    }
    public void setBlock(String block) {
        this.block = block;
    }

    @Basic
    @Column(name = "flat")
    public String getFlat() {
        return flat;
    }
    public void setFlat(String flat) {
        this.flat = flat;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(INDEX, index);
        json.put(REGION, region);
        json.put(DISTRICT, district);
        json.put(CITY, city);
        json.put(STREET, street);
        json.put(BUILD, build);
        json.put(BLOCK, block);
        json.put(FLAT, flat);
        return json;
    }

    @Transient
    public String getValue() {
        return city + SPACE + street + SPACE + build;
    }
}
