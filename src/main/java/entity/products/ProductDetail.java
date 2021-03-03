package entity.products;

import javax.persistence.*;

@Entity
@Table(name = "product_details")
public class ProductDetail {
    private int id;
    private Product parent;
    private String path;
    private String name;

    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
