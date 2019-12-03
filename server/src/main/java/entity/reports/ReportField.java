package entity.reports;

import entity.weight.Unit;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
@Entity
@Table(name = "report_fields")
public class ReportField implements Comparable<ReportField> {
    private int id;
    private ReportFieldCategory category;
    private ManufactureReport report;
    private String title;
    private float value;
    private Unit unit;
    private String comment;
    private int index;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "category")
    public ReportFieldCategory getCategory() {
        return category;
    }
    public void setCategory(ReportFieldCategory category) {
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name = "report")
    public ManufactureReport getReport() {
        return report;
    }
    public void setReport(ManufactureReport report) {
        this.report = report;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "value")
    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }

    @OneToOne
    @JoinColumn(name = "unit")
    public Unit getUnit() {
        return unit;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Basic
    @Column(name = "index")
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int compareTo(ReportField o) {
        if (o != null) {
            int compare;
            if (category != null){
                compare = category.compareTo(o.category);
            } else {
                compare = 1;
            }
            return compare;
        }
        return 0;
    }
}
