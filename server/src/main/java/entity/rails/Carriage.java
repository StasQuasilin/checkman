package entity.rails;

import entity.documents.Deal;

import javax.persistence.*;
import java.util.List;

/**
 * Created by szpt_user045 on 07.10.2019.
 */
@Entity
@Table(name = "carriages")
public class Carriage {
    private int id;
    private Deal deal;
    private String number;
    private List<CarriageLoadPlan> plans;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "deal")
    public Deal getDeal() {
        return deal;
    }
    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    @Basic
    @Column(name = "number")
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "carriage", cascade = CascadeType.ALL)
    public List<CarriageLoadPlan> getPlans() {
        return plans;
    }
    public void setPlans(List<CarriageLoadPlan> plans) {
        this.plans = plans;
    }
}
