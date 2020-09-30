package entity.references;

import constants.Keys;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product extends JsonAble {
    private int id;
    private Product parent;
    private String name;
    private boolean service;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_parent")
    public Product getParent() {
        return parent;
    }
    public void setParent(Product parent) {
        this.parent = parent;
    }

    @Basic
    @Column(name = "_name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "_service")
    public boolean isService() {
        return service;
    }
    public void setService(boolean service) {
        this.service = service;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(Keys.ID, id);
        jsonObject.put(Keys.NAME, name);
        return jsonObject;
    }
}
