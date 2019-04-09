package entity.laboratory.subdivisions.extraction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by szpt_user045 on 04.04.2019.
 */
@Entity
@Table(name = "turns_extraction")
public class ExtractionTurn {
    private int id;
    private Timestamp date;
    private int number;
    private Set<ExtractionCrude> crudes;
    private Set<ExtractionRaw> raws;
    private ExtractionOIl oil;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "name")
    public int getNumber() {
        return number;
    }
    public void setNumber(int name) {
        this.number = name;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<ExtractionCrude> getCrudes() {
        return crudes;
    }
    public void setCrudes(Set<ExtractionCrude> crudes) {
        this.crudes = crudes;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<ExtractionRaw> getRaws() {
        return raws;
    }
    public void setRaws(Set<ExtractionRaw> raws) {
        this.raws = raws;
    }

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public ExtractionOIl getOil() {
        return oil;
    }
    public void setOil(ExtractionOIl oil) {
        this.oil = oil;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * date.hashCode() + hash;
        hash = 31 * number + hash;
        for (ExtractionCrude crude : crudes) {
            hash = 31 * crude.hashCode() + hash;
        }

        for (ExtractionRaw raw : raws) {
            hash = 31 * raw.hashCode() + hash;
        }

        if (oil != null) {
            hash = 31 * oil.hashCode() + hash;
        }
        return hash;
    }
}
