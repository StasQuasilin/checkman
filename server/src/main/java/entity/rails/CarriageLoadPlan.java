package entity.rails;

import entity.documents.LoadPlan;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 07.10.2019.
 */
@Entity
@Table(name = "carriage_load_plans")
public class CarriageLoadPlan {
    private int id;
    private Carriage carriage;
    private float plan;
    private LoadPlan loadPlan;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "carriage")
    public Carriage getCarriage() {
        return carriage;
    }
    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }

    @OneToOne
    @JoinColumn(name = "load_plan")
    public LoadPlan getLoadPlan() {
        return loadPlan;
    }
    public void setLoadPlan(LoadPlan loadPlan) {
        this.loadPlan = loadPlan;
    }
}
