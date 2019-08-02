package entity.rails;

import constants.Branches;
import entity.documents.Deal;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by szpt_user045 on 30.05.2019.
 */
@Entity
@Table(name = "trains")
public class Train {
    private long id;
    private Deal deal;
    private Set<Truck> trucks;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }
    public void setId(long id) {
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "train", cascade = CascadeType.ALL)
    public Set<Truck> getTrucks() {
        return trucks;
    }
    public void setTrucks(Set<Truck> trucks) {
        this.trucks = trucks;
    }
}
