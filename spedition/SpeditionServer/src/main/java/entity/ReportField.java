package entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "report_fields")
public class ReportField {
    private int id;
    private int index;
    private Timestamp arriveTime;
    private Product product;
    private DealType dealType;
    private Weight weight;

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
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    @Basic
    @Column(name = "arrive_time")
    public Timestamp getArriveTime() {
        return arriveTime;
    }
    public void setArriveTime(Timestamp arriveTime) {
        this.arriveTime = arriveTime;
    }

    @OneToOne
    @JoinColumn(name = "product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "deal_type")
    public DealType getDealType() {
        return dealType;
    }
    public void setDealType(DealType dealType) {
        this.dealType = dealType;
    }

    @OneToOne
    @JoinColumn(name = "weight")
    public Weight getWeight() {
        return weight;
    }
    public void setWeight(Weight weight) {
        this.weight = weight;
    }
}
