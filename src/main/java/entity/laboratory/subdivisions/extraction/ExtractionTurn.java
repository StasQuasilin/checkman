package entity.laboratory.subdivisions.extraction;

import entity.production.Turn;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by szpt_user045 on 04.04.2019.
 */
@Entity
@Table(name = "turns_extraction")
public class ExtractionTurn {
    private int id;
    private Date date;
    private Turn turn;
    private Set<ExtractionCrude> crudes;
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
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
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
    public Set<ExtractionCrude> getCrudes() {
        return crudes;
    }
    public void setCrudes(Set<ExtractionCrude> crudes) {
        this.crudes = crudes;
    }

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public ExtractionOIl getOil() {
        return oil;
    }
    public void setOil(ExtractionOIl oil) {
        this.oil = oil;
    }
}
