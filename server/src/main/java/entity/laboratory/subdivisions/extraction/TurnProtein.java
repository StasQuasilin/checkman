package entity.laboratory.subdivisions.extraction;

import entity.JsonAble;
import entity.Worker;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 16.05.2019.
 */
@Entity
@Table(name = "extraction_turn_protein")
public class TurnProtein extends JsonAble{
    private int id;
    ExtractionTurn turn;
    private float protein;
    private float humidity;
    private float nuclearGrease;
    private ActionTime createTime;
    private Worker creator;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "turn")
    public ExtractionTurn getTurn() {
        return turn;
    }
    public void setTurn(ExtractionTurn turn) {
        this.turn = turn;
    }

    @Basic
    @Column(name = "protein")
    public float getProtein() {
        return protein;
    }
    public void setProtein(float oiliness) {
        this.protein = oiliness;
    }

    @Basic
    @Column(name = "humidity")
    public float getHumidity() {
        return humidity;
    }
    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    @Basic
    @Column(name = "nuclear")
    public float getNuclearGrease() {
        return nuclearGrease;
    }
    public void setNuclearGrease(float nuclearGrease) {
        this.nuclearGrease = nuclearGrease;
    }

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }

    @OneToOne
    @JoinColumn(name = "creator")
    public Worker getCreator() {
        return creator;
    }
    public void setCreator(Worker creator) {
        this.creator = creator;
    }

    @Transient
    public float DryRecalculation(){
        if (protein > 0 && humidity > 0){
            return protein * 100 / (100 - humidity);
        }
        return 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * Float.hashCode(protein) + hash;
        hash = 31 * Float.hashCode(humidity) + hash;
        return hash;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(PROTEIN, protein);
        json.put(HUMIDITY, humidity);
        json.put(NUCLEAR, nuclearGrease);
        return json;
    }
}
