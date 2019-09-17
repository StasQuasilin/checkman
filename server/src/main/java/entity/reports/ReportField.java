package entity.reports;

import entity.storages.Storage;
import entity.weight.WeightUnit;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
@Entity
@Table(name = "report_fields")
public class ReportField {
    private int id;
    private ReportFieldCategory category;
    private ManufactureReport report;
    private String title;
    private Storage storage;
    private float value;
    private WeightUnit unit;
    private String comment;

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

    @OneToOne
    @JoinColumn(name = "storage")
    public Storage getStorage() {
        return storage;
    }
    public void setStorage(Storage storage) {
        this.storage = storage;
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
    public WeightUnit getUnit() {
        return unit;
    }
    public void setUnit(WeightUnit unit) {
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
}
