package entity.organisations;

import constants.Constants;
import entity.JsonAble;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;
import utils.U;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "organisations")
public class Organisation extends JsonAble{
    private int id;
    private String code;
    private String type;
    private String name;
    private OrganisationWe organisationWe;
    private ActionTime create;


    public static final String EMPTY = Constants.EMPTY;
    public static final String COMMA = Constants.COMMA + Constants.SPACE;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @JoinColumn(name = "type")
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @OneToOne(mappedBy = "organisation")
    public OrganisationWe getOrganisationWe() {
        return organisationWe;
    }
    public void setOrganisationWe(OrganisationWe organisationWe) {
        this.organisationWe = organisationWe;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @OneToOne
    @JoinColumn(name = "created")
    public ActionTime getCreate() {
        return create;
    }
    public void setCreate(ActionTime create) {
        this.create = create;
    }

    @Transient
    public String getValue() {
        return name + (U.exist(type) ? COMMA + type: EMPTY);
    }

    @Override
    public String toString() {
        return "Organisation{\n" +
                "\tid=" + id + ",\n" +
                "\ttype='" + (type != null ? type : "") + "',\n" +
                "\tname='" + name + "'\n" +
                '}';
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public JSONObject toShortJson() {
        JSONObject json = pool.getObject();
        if (code != null) {
            json.put(CODE, code);
        }
        json.put(ID, id);
        json.put(VALUE, getValue());
        return json;
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject json = toShortJson();
        json.put(NAME, name);
        json.put(TYPE, type);
        json.put(Constants.WE, organisationWe != null);
        return json;
    }
}
