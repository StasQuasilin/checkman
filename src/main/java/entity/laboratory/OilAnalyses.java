package entity.laboratory;

import entity.JsonAble;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@Entity
@Table(name = "analyses_oil")
public class OilAnalyses extends JsonAble{
    private int id;
    private boolean organoleptic;
    private float color;
    private float acidValue;
    private float peroxideValue;
    private float phosphorus;
    private float humidity;
    private boolean soap;
    private float wax;
    private boolean waxB;
    private float degreaseImpurity;
    private float transparency;
    private float benzopyrene;
    private float explosion;
    private ActionTime createTime;
    private int act;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "organoleptic")
    public boolean isOrganoleptic() {
        return organoleptic;
    }
    public void setOrganoleptic(boolean organoleptic) {
        this.organoleptic = organoleptic;
    }

    @Basic
    @Column(name = "color")
    public float getColor() {
        return color;
    }
    public void setColor(float color) {
        this.color = color;
    }

    @Basic
    @Column(name = "acid_value")
    public float getAcidValue() {
        return acidValue;
    }
    public void setAcidValue(float acidValue) {
        this.acidValue = acidValue;
    }

    @Basic
    @Column(name = "peroxide")
    public float getPeroxideValue() {
        return peroxideValue;
    }
    public void setPeroxideValue(float peroxideValue) {
        this.peroxideValue = peroxideValue;
    }

    @Basic
    @Column(name = "phosphorus")
    public float getPhosphorus() {
        return phosphorus;
    }
    public void setPhosphorus(float phosphorus) {
        this.phosphorus = phosphorus;
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
    @Column(name = "soap")
    public boolean isSoap() {
        return soap;
    }
    public void setSoap(boolean soap) {
        this.soap = soap;
    }

    @Basic
    @Column(name = "wax")
    public float getWax() {
        return wax;
    }
    public void setWax(float wax) {
        this.wax = wax;
    }

    @Basic
    @Column(name = "degrease_impurity")
    public float getDegreaseImpurity() {
        return degreaseImpurity;
    }
    public void setDegreaseImpurity(float degreaseImpurity) {
        this.degreaseImpurity = degreaseImpurity;
    }

    @Basic
    @Column(name = "transparency")
    public float getTransparency() {
        return transparency;
    }
    public void setTransparency(float transparency) {
        this.transparency = transparency;
    }

    @Basic
    @Column(name = "benzopyrene")
    public float getBenzopyrene() {
        return benzopyrene;
    }
    public void setBenzopyrene(float benzopyrene) {
        this.benzopyrene = benzopyrene;
    }

    @Basic
    @Column(name = "explosion")
    public float getExplosion() {
        return explosion;
    }
    public void setExplosion(float explosion) {
        this.explosion = explosion;
    }

    @Basic
    @Column(name = "wax_b")
    public boolean isWaxB() {
        return waxB;
    }
    public void setWaxB(boolean waxB) {
        this.waxB = waxB;
    }

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "act")
    public int getAct() {
        return act;
    }
    public void setAct(int act) {
        this.act = act;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(ORGANOLEPTIC, organoleptic);
        json.put(COLOR, color);
        json.put(ACID, acidValue);
        json.put(PEROXIDE, peroxideValue);
        json.put(PHOSPHORUS, phosphorus);
        json.put(HUMIDITY, humidity);
        json.put(SOAP, soap);
        json.put(WAX, wax);
        json.put(CREATE, createTime.toJson());
        return json;
    }
}
