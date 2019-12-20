package entity.laboratory;

import entity.products.Product;
import entity.transport.ActionTime;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by szpt_user045 on 18.12.2019.
 */
@Entity
@Table(name = "protocols")
public class Protocol {
    private int id;
    private Date date;
    private Product product;
    private String number;
    private ActionTime createTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date time) {
        this.date = time;
    }

    @OneToOne
    @JoinColumn(name = "product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @Basic
    @Column(name = "_number")
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }
}
