package entity.reports;

import entity.Worker;
import entity.production.Turn;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
@Entity
@Table(name = "manufacture_reports")
public class ManufactureReport {
    private int id;
    private Turn turn;
    private Worker creator;
    private List<ReportField> fields = new ArrayList<>();

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

    @OneToOne
    @JoinColumn(name = "creator")
    public Worker getCreator() {
        return creator;
    }
    public void setCreator(Worker creator) {
        this.creator = creator;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "report", cascade = CascadeType.ALL)
    public List<ReportField> getFields() {
        return fields;
    }
    public void setFields(List<ReportField> fields) {
        this.fields = fields;
    }
}
