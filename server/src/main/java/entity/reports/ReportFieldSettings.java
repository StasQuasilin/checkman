package entity.reports;

import entity.storages.Storage;
import entity.weight.WeightUnit;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
@Entity
@Table(name = "report_field_settings")
public class ReportFieldSettings {
    private int id;
    private String title;
    private WeightUnit unit;
    private ReportFieldCategory category;
    private Storage storage;
    private String calculator;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    @JoinColumn(name = "unit")
    public WeightUnit getUnit() {
        return unit;
    }
    public void setUnit(WeightUnit unit) {
        this.unit = unit;
    }

    @OneToOne
    @JoinColumn(name = "category")
    public ReportFieldCategory getCategory() {
        return category;
    }
    public void setCategory(ReportFieldCategory category) {
        this.category = category;
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
    @Column(name = "calculator")
    public String getCalculator() {
        return calculator;
    }
    public void setCalculator(String calculator) {
        this.calculator = calculator;
    }
}
