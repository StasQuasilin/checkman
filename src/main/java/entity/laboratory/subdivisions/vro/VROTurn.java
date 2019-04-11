package entity.laboratory.subdivisions.vro;

import entity.laboratory.subdivisions.extraction.ExtractionCrude;
import entity.laboratory.subdivisions.extraction.ExtractionOIl;
import entity.laboratory.subdivisions.extraction.ExtractionRaw;
import entity.seals.Seal;

import javax.persistence.*;
import javax.swing.plaf.basic.BasicScrollPaneUI;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by szpt_user045 on 10.04.2019.
 */
@Entity
@Table(name = "turns_vro")
public class VROTurn {
    private int id;
    private Timestamp date;
    private int number;
    private Set<VROCrude> crudes;
    private Set<VROOil> oils;
    private Set<VRODaily> dailies;

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
    @Column(name = "number")
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<VROCrude> getCrudes() {
        return crudes;
    }
    public void setCrudes(Set<VROCrude> crudes) {
        this.crudes = crudes;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<VROOil> getOils() {
        return oils;
    }
    public void setOils(Set<VROOil> oils) {
        this.oils = oils;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<VRODaily> getDailies() {
        return dailies;
    }
    public void setDailies(Set<VRODaily> dailies) {
        this.dailies = dailies;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * date.hashCode() + hash;
        hash = 31 * number + hash;

        for (VROCrude crude : crudes){
            hash = 31 * crude.hashCode() + hash;
        }

        for (VROOil oil : oils) {
            hash = 31 * oil.hashCode() + hash;
        }

        for (VRODaily daily : dailies) {
            hash = 31 * daily.hashCode() + hash;
        }

        return hash;
    }
}
