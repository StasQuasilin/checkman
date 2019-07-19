package entity.laboratory.transportation;

import entity.laboratory.MealAnalyses;
import entity.transport.Transportation;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@Entity
@Table(name = "analyses_transportation_cake")
public class CakeTransportationAnalyses {
    private int id;
    private Transportation transportation;
    private MealAnalyses analyses;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "transportation")
    public Transportation getTransportation() {
        return transportation;
    }
    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    @OneToOne
    @JoinColumn(name = "analyses")
    public MealAnalyses getAnalyses() {
        return analyses;
    }
    public void setAnalyses(MealAnalyses analyses) {
        this.analyses = analyses;
    }

    @Override
    public int hashCode() {
        return analyses.hashCode();
    }
}
