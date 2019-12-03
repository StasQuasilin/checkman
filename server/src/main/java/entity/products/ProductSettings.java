package entity.products;

import constants.Constants;
import entity.JsonAble;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 03.12.2019.
 */
@Entity
@Table(name = "product_settings")
public class ProductSettings extends JsonAble{

    final static String SLASH = Constants.SLASH;

    private int id;
    private String path;
    private Product product;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    @OneToOne
    @JoinColumn(name = "product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @Transient
    public ArrayList<String> getFullPath() {
        ArrayList<String> strings = new ArrayList<>();
        if (product.getProductGroup() != null){
            strings.add(product.getProductGroup().getName());
        }
        if (path != null){
            Collections.addAll(strings, getSplitPath());
        }
        return strings;

    }

    @Transient
    public String[] getSplitPath(){
        return path.split(SLASH);
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        JSONArray array = pool.getArray();
        array.addAll(getFullPath().stream().collect(Collectors.toList()));
        object.put(PATH, array);
        object.put(PRODUCT, product.toJson());
        return object;
    }
}
