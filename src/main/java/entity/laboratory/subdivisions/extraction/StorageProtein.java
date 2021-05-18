package entity.laboratory.subdivisions.extraction;

import entity.JsonAble;
import entity.storages.Storage;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 15.05.2019.
 */
@Entity
@Table(name = "extraction_storage_protein")
public class StorageProtein extends JsonAble{
    private int id;
    private ExtractionTurn turn;
    private Timestamp time;
    private Storage storage;
    private float protein;
    private float humidity;
    private float nuclearGrease;
    private ActionTime createTime;

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
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @OneToOne
    @JoinColumn(name = "storage")
    public Storage getStorage() {
        return storage;
    }
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Basic
    @Column(name = "protein")
    public float getProtein() {
        return protein;
    }
    public void setProtein(float protein) {
        this.protein = protein;
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

    @Transient
    public float DryRecalculation(){
        if (protein > 0 && humidity > 0){
            return protein * 100 / (100 - humidity);
        }
        return 0;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(TIME, time.toString());
        object.put(PROTEIN, protein);
        object.put(HUMIDITY, humidity);
        object.put(NUCLEAR, nuclearGrease);
        return object;
    }
}
