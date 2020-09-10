package entity.rails;

import constants.Keys;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trains")
public class Train extends JsonAble {
    private int id;
    private Date date;
    private Set<Carriage> carriages = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public void setDate(Date date) {
        this.date = date;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "train", cascade = CascadeType.ALL)
    public Set<Carriage> getCarriages() {
        return carriages;
    }
    public void setCarriages(Set<Carriage> carriages) {
        this.carriages = carriages;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(Keys.ID, id);
        jsonObject.put(Keys.DATE, date.toString());
        jsonObject.put(Keys.CARRIAGES, carriages());
        return jsonObject;
    }

    private JSONArray carriages() {
        final JSONArray array = new JSONArray();
        for (Carriage carriage : carriages){
            array.add(carriage.toJson());
        }
        return array;
    }
}
