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
@Table(name = "extraction_storage_grease")
public class StorageGrease extends JsonAble{
    private int id;
    private ExtractionTurn turn;
    private Timestamp time;
    private Storage storage;
    private float grease;
    private float humidity;
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
    @Column(name = "grease")
    public float getGrease() {
        return grease;
    }
    public void setGrease(float grease) {
        this.grease = grease;
    }

    @Basic
    @Column(name = "humidity")
    public float getHumidity() {
        return humidity;
    }
    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(TIME, time.toString());
        json.put(GREASE, grease);
        json.put(HUMIDITY, humidity);
        return json;
    }
}
