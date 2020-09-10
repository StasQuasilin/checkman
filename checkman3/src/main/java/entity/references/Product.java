package entity.references;

import entity.analyses.AnalysesType;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

import static constants.Keys.*;

@Entity
@Table(name = "products")
public class Product extends JsonAble {
    private int id;
    private String name;
    private AnalysesType analysesType;
    private boolean group;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_analyses_type")
    public AnalysesType getAnalysesType() {
        return analysesType;
    }
    public void setAnalysesType(AnalysesType analysesType) {
        this.analysesType = analysesType;
    }

    @Basic
    @Column(name = "_group")
    public boolean isGroup() {
        return group;
    }
    public void setGroup(boolean group) {
        this.group = group;
    }

    @Override
    public JSONObject toShortJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(NAME, name);
        return jsonObject;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = toShortJson();
        if (analysesType != null) {
            jsonObject.put(ANALYSES_TYPE, analysesType);
        }
        return jsonObject;
    }
}
