package entity.products;

import org.glassfish.jersey.server.BackgroundScheduler;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "product_group")
public class ProductGroup {
    private int id;
    private String name;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
