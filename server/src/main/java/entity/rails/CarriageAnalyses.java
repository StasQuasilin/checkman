package entity.rails;

import entity.laboratory.MealAnalyses;
import entity.laboratory.OilAnalyses;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 07.10.2019.
 */
@Entity
@Table(name = "carriage_analyses")
public class CarriageAnalyses {
    private int id;
    private Carriage carriage;
    private OilAnalyses oilAnalyses;
    private MealAnalyses mealAnalyses;

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
    @JoinColumn(name = "oil")
    public OilAnalyses getOilAnalyses() {
        return oilAnalyses;
    }
    public void setOilAnalyses(OilAnalyses oilAnalyses) {
        this.oilAnalyses = oilAnalyses;
    }

    @OneToOne
    @JoinColumn(name = "meal")
    public MealAnalyses getMealAnalyses() {
        return mealAnalyses;
    }
    public void setMealAnalyses(MealAnalyses mealAnalyses) {
        this.mealAnalyses = mealAnalyses;
    }
}
