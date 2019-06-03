package entity.laboratory.subdivisions.vro;

import entity.laboratory.subdivisions.extraction.ExtractionCrude;
import entity.laboratory.subdivisions.extraction.ExtractionOIl;
import entity.laboratory.subdivisions.extraction.ExtractionRaw;
import entity.production.Turn;
import entity.seals.Seal;

import javax.persistence.*;
import javax.swing.plaf.basic.BasicScrollPaneUI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * Created by szpt_user045 on 10.04.2019.
 */
@Entity
@Table(name = "turns_vro")
public class VROTurn {
    private int id;
    private Turn turn;
    private List<VROCrude> crudes;
    private Set<VROOil> oils;
    private Set<VRODaily> dailies;
    private Set<OilMassFraction> oilMassFractions;
    private Set<OilMassFractionDry> oilMassFractionDries;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "turn")
    public Turn getTurn() {
        return turn;
    }
    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public List<VROCrude> getCrudes() {
        return crudes;
    }
    public void setCrudes(List<VROCrude> crudes) {
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<OilMassFraction> getOilMassFractions() {
        return oilMassFractions;
    }
    public void setOilMassFractions(Set<OilMassFraction> oilMassFractions) {
        this.oilMassFractions = oilMassFractions;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<OilMassFractionDry> getOilMassFractionDries() {
        return oilMassFractionDries;
    }
    public void setOilMassFractionDries(Set<OilMassFractionDry> oilMassFractionDries) {
        this.oilMassFractionDries = oilMassFractionDries;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * turn.hashCode() + hash;

        for (VROCrude crude : crudes){
            hash = 31 * crude.hashCode() + hash;
        }

        for (VROOil oil : oils) {
            hash = 31 * oil.hashCode() + hash;
        }

        for (VRODaily daily : dailies) {
            hash = 31 * daily.hashCode() + hash;
        }

        for (OilMassFraction omf : oilMassFractions) {
            hash = 31 * omf.hashCode() + hash;
        }

        for (OilMassFractionDry omfD : oilMassFractionDries){
            hash = 31 * omfD.hashCode() + hash;
        }

        return hash;
    }
}
