package entity.organisations;

import constants.Constants;
import entity.transport.ActionTime;
import utils.U;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "organisations")
public class Organisation {

    private int id;
    private String type;
    private String name;
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
    @JoinColumn(name = "type")
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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
                "\ttype=\'" + (type != null ? type : "") + "\',\n" +
                "\tname='" + name + "\'\n" +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * type.hashCode() + hash;
        hash = 31 * name.hashCode() + hash;

        return hash;
    }
}
